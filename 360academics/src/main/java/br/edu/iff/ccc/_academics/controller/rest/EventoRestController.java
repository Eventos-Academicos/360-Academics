package br.edu.iff.ccc._academics.controller.rest;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.iff.ccc._academics.dto.EventoRequest;
import br.edu.iff.ccc._academics.entities.Evento;
import br.edu.iff.ccc._academics.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/eventos")
@Tag(name = "Eventos", description = "Cadastro e consulta dos eventos acadêmicos")
public class EventoRestController {

    private final EventoService service;

    public EventoRestController(EventoService service) {
        this.service = service;
    }

    @Operation(summary = "Listar eventos", description = "Retorna todos os eventos acadêmicos cadastrados.")
    @ApiResponse(responseCode = "200", description = "Listagem recuperada com sucesso")
    @GetMapping
    public ResponseEntity<List<Evento>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Buscar evento por ID", description = "Recupera um evento específico pelo seu UUID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Evento encontrado"),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado (formato RFC 9457)")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Evento> buscar(
            @Parameter(description = "UUID do evento", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar evento",
            description = "Cria um novo evento acadêmico. O nome deve ser único na plataforma.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Evento criado; o endereço do novo recurso vem no cabeçalho Location"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos no corpo da requisição (formato RFC 9457)"),
        @ApiResponse(responseCode = "409", description = "Já existe um evento com esse nome (formato RFC 9457)")
    })
    @PostMapping
    public ResponseEntity<Void> criar(@Valid @RequestBody EventoRequest request) {
        Evento evento = service.criar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(evento.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Atualizar evento", description = "Substitui os dados de um evento existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Evento atualizado com sucesso (sem conteúdo no retorno)"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos no corpo da requisição (formato RFC 9457)"),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado (formato RFC 9457)"),
        @ApiResponse(responseCode = "409", description = "Já existe outro evento com esse nome (formato RFC 9457)")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @Parameter(description = "UUID do evento") @PathVariable UUID id,
            @Valid @RequestBody EventoRequest request) {
        service.atualizar(id, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remover evento",
            description = "Exclui um evento que não possua atividades ou inscrições vinculadas.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Evento removido com sucesso (sem conteúdo no retorno)"),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado (formato RFC 9457)"),
        @ApiResponse(responseCode = "409", description = "Evento ainda vinculado a atividades ou inscrições (formato RFC 9457)")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@Parameter(description = "UUID do evento") @PathVariable UUID id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

}
