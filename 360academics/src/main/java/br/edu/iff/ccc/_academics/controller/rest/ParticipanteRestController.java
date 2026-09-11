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

import br.edu.iff.ccc._academics.dto.ParticipanteRequest;
import br.edu.iff.ccc._academics.entities.Participante;
import br.edu.iff.ccc._academics.service.ParticipanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/participantes")
@Tag(name = "Participantes", description = "Cadastro dos estudantes que se inscrevem nos eventos")
public class ParticipanteRestController {

    private final ParticipanteService service;

    public ParticipanteRestController(ParticipanteService service) {
        this.service = service;
    }

    @Operation(summary = "Listar participantes", description = "Retorna todos os participantes cadastrados.")
    @ApiResponse(responseCode = "200", description = "Listagem recuperada com sucesso")
    @GetMapping
    public ResponseEntity<List<Participante>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Buscar participante por ID", description = "Recupera um participante específico pelo seu UUID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Participante encontrado"),
        @ApiResponse(responseCode = "404", description = "Participante não encontrado (formato RFC 9457)")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Participante> buscar(
            @Parameter(description = "UUID do participante", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar participante",
            description = "Cria um novo participante. O e-mail deve ser único entre todos os usuários.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Participante criado; o endereço do novo recurso vem no cabeçalho Location"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos no corpo da requisição (formato RFC 9457)"),
        @ApiResponse(responseCode = "409", description = "Já existe um usuário com esse e-mail (formato RFC 9457)")
    })
    @PostMapping
    public ResponseEntity<Void> criar(@Valid @RequestBody ParticipanteRequest request) {
        Participante participante = service.criar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(participante.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Atualizar participante", description = "Substitui os dados de um participante existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Participante atualizado com sucesso (sem conteúdo no retorno)"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos no corpo da requisição (formato RFC 9457)"),
        @ApiResponse(responseCode = "404", description = "Participante não encontrado (formato RFC 9457)"),
        @ApiResponse(responseCode = "409", description = "Já existe outro usuário com esse e-mail (formato RFC 9457)")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @Parameter(description = "UUID do participante") @PathVariable UUID id,
            @Valid @RequestBody ParticipanteRequest request) {
        service.atualizar(id, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remover participante",
            description = "Exclui um participante que não possua inscrições ou histórico vinculados.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Participante removido com sucesso (sem conteúdo no retorno)"),
        @ApiResponse(responseCode = "404", description = "Participante não encontrado (formato RFC 9457)"),
        @ApiResponse(responseCode = "409", description = "Participante ainda vinculado a inscrições ou histórico (formato RFC 9457)")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@Parameter(description = "UUID do participante") @PathVariable UUID id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

}
