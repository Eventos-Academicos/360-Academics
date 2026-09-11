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

import br.edu.iff.ccc._academics.dto.AtividadeRequest;
import br.edu.iff.ccc._academics.entities.Atividade;
import br.edu.iff.ccc._academics.service.AtividadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * A listagem e o cadastro ficam aninhados ao evento, porque toda atividade
 * pertence a um evento; as operações sobre uma atividade específica usam só o
 * seu próprio UUID.
 */
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Atividades", description = "Palestras, minicursos e oficinas vinculados a um evento")
public class AtividadeRestController {

    private final AtividadeService service;

    public AtividadeRestController(AtividadeService service) {
        this.service = service;
    }

    @Operation(summary = "Listar atividades de um evento",
            description = "Retorna as atividades cadastradas no evento informado.")
    @ApiResponse(responseCode = "200", description = "Listagem recuperada com sucesso")
    @GetMapping("/eventos/{eventoId}/atividades")
    public ResponseEntity<List<Atividade>> listarPorEvento(
            @Parameter(description = "UUID do evento", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID eventoId) {
        return ResponseEntity.ok(service.listarPorEvento(eventoId));
    }

    @Operation(summary = "Buscar atividade por ID", description = "Recupera uma atividade específica pelo seu UUID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Atividade encontrada"),
        @ApiResponse(responseCode = "404", description = "Atividade não encontrada (formato RFC 9457)")
    })
    @GetMapping("/atividades/{id}")
    public ResponseEntity<Atividade> buscar(
            @Parameter(description = "UUID da atividade") @PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar atividade em um evento",
            description = "Cria uma nova atividade no evento informado, vinculada a um palestrante.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Atividade criada; o endereço do novo recurso vem no cabeçalho Location"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos no corpo da requisição (formato RFC 9457)"),
        @ApiResponse(responseCode = "404", description = "Evento ou palestrante não encontrado (formato RFC 9457)")
    })
    @PostMapping("/eventos/{eventoId}/atividades")
    public ResponseEntity<Void> criar(
            @Parameter(description = "UUID do evento") @PathVariable UUID eventoId,
            @Valid @RequestBody AtividadeRequest request) {
        Atividade atividade = service.criar(eventoId, request);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/atividades/{id}").buildAndExpand(atividade.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Atualizar atividade", description = "Substitui os dados de uma atividade existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Atividade atualizada com sucesso (sem conteúdo no retorno)"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos no corpo da requisição (formato RFC 9457)"),
        @ApiResponse(responseCode = "404", description = "Atividade ou palestrante não encontrado (formato RFC 9457)")
    })
    @PutMapping("/atividades/{id}")
    public ResponseEntity<Void> atualizar(
            @Parameter(description = "UUID da atividade") @PathVariable UUID id,
            @Valid @RequestBody AtividadeRequest request) {
        service.atualizar(id, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remover atividade",
            description = "Exclui uma atividade que não possua presenças ou certificados vinculados.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Atividade removida com sucesso (sem conteúdo no retorno)"),
        @ApiResponse(responseCode = "404", description = "Atividade não encontrada (formato RFC 9457)"),
        @ApiResponse(responseCode = "409", description = "Atividade ainda vinculada a presenças ou certificados (formato RFC 9457)")
    })
    @DeleteMapping("/atividades/{id}")
    public ResponseEntity<Void> remover(@Parameter(description = "UUID da atividade") @PathVariable UUID id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

}
