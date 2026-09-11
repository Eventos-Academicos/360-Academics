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

import br.edu.iff.ccc._academics.dto.PalestranteRequest;
import br.edu.iff.ccc._academics.entities.Palestrante;
import br.edu.iff.ccc._academics.service.PalestranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/palestrantes")
@Tag(name = "Palestrantes", description = "Cadastro dos palestrantes responsáveis pelas atividades")
public class PalestranteRestController {

    private final PalestranteService service;

    public PalestranteRestController(PalestranteService service) {
        this.service = service;
    }

    @Operation(summary = "Listar palestrantes", description = "Retorna todos os palestrantes cadastrados.")
    @ApiResponse(responseCode = "200", description = "Listagem recuperada com sucesso")
    @GetMapping
    public ResponseEntity<List<Palestrante>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Buscar palestrante por ID", description = "Recupera um palestrante específico pelo seu UUID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Palestrante encontrado"),
        @ApiResponse(responseCode = "404", description = "Palestrante não encontrado (formato RFC 9457)")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Palestrante> buscar(
            @Parameter(description = "UUID do palestrante", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar palestrante",
            description = "Cria um novo palestrante. O e-mail deve ser único entre todos os usuários.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Palestrante criado; o endereço do novo recurso vem no cabeçalho Location"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos no corpo da requisição (formato RFC 9457)"),
        @ApiResponse(responseCode = "409", description = "Já existe um usuário com esse e-mail (formato RFC 9457)")
    })
    @PostMapping
    public ResponseEntity<Void> criar(@Valid @RequestBody PalestranteRequest request) {
        Palestrante palestrante = service.criar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(palestrante.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Atualizar palestrante",
            description = "Substitui os dados de um palestrante existente. Senha em branco mantém a atual.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Palestrante atualizado com sucesso (sem conteúdo no retorno)"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos no corpo da requisição (formato RFC 9457)"),
        @ApiResponse(responseCode = "404", description = "Palestrante não encontrado (formato RFC 9457)"),
        @ApiResponse(responseCode = "409", description = "Já existe outro usuário com esse e-mail (formato RFC 9457)")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @Parameter(description = "UUID do palestrante") @PathVariable UUID id,
            @Valid @RequestBody PalestranteRequest request) {
        service.atualizar(id, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remover palestrante",
            description = "Exclui um palestrante que não seja responsável por nenhuma atividade.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Palestrante removido com sucesso (sem conteúdo no retorno)"),
        @ApiResponse(responseCode = "404", description = "Palestrante não encontrado (formato RFC 9457)"),
        @ApiResponse(responseCode = "409", description = "Palestrante ainda responsável por atividades (formato RFC 9457)")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@Parameter(description = "UUID do palestrante") @PathVariable UUID id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

}
