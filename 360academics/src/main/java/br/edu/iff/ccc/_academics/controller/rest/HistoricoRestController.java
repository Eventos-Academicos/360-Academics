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

import br.edu.iff.ccc._academics.dto.HistoricoRequest;
import br.edu.iff.ccc._academics.entities.HistoricoParticipacao;
import br.edu.iff.ccc._academics.service.HistoricoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/historico")
@Tag(name = "Histórico de Participação", description = "Registro dos eventos concluídos por cada participante")
public class HistoricoRestController {

    private final HistoricoService service;

    public HistoricoRestController(HistoricoService service) {
        this.service = service;
    }

    @Operation(summary = "Listar histórico", description = "Retorna todos os registros de participação concluída.")
    @ApiResponse(responseCode = "200", description = "Listagem recuperada com sucesso")
    @GetMapping
    public ResponseEntity<List<HistoricoParticipacao>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Buscar registro por ID", description = "Recupera um registro de histórico pelo seu UUID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registro encontrado"),
        @ApiResponse(responseCode = "404", description = "Registro não encontrado (formato RFC 9457)")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HistoricoParticipacao> buscar(
            @Parameter(description = "UUID do registro", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Registrar participação concluída",
            description = "Registra que um participante concluiu um evento.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Registro criado; o endereço do novo recurso vem no cabeçalho Location"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos no corpo da requisição (formato RFC 9457)"),
        @ApiResponse(responseCode = "404", description = "Participante ou evento não encontrado (formato RFC 9457)")
    })
    @PostMapping
    public ResponseEntity<Void> criar(@Valid @RequestBody HistoricoRequest request) {
        HistoricoParticipacao item = service.criar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(item.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Atualizar registro", description = "Substitui os dados de um registro de histórico existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Registro atualizado com sucesso (sem conteúdo no retorno)"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos no corpo da requisição (formato RFC 9457)"),
        @ApiResponse(responseCode = "404", description = "Registro, participante ou evento não encontrado (formato RFC 9457)")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @Parameter(description = "UUID do registro") @PathVariable UUID id,
            @Valid @RequestBody HistoricoRequest request) {
        service.atualizar(id, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remover registro", description = "Exclui um registro de histórico.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Registro removido com sucesso (sem conteúdo no retorno)"),
        @ApiResponse(responseCode = "404", description = "Registro não encontrado (formato RFC 9457)")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@Parameter(description = "UUID do registro") @PathVariable UUID id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

}
