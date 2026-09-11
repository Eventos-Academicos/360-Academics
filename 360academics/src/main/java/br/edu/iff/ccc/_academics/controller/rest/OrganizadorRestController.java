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

import br.edu.iff.ccc._academics.dto.OrganizadorRequest;
import br.edu.iff.ccc._academics.entities.Organizador;
import br.edu.iff.ccc._academics.service.OrganizadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/organizadores")
@Tag(name = "Organizadores", description = "Cadastro dos responsáveis pela gestão dos eventos")
public class OrganizadorRestController {

    private final OrganizadorService service;

    public OrganizadorRestController(OrganizadorService service) {
        this.service = service;
    }

    @Operation(summary = "Listar organizadores", description = "Retorna todos os organizadores cadastrados.")
    @ApiResponse(responseCode = "200", description = "Listagem recuperada com sucesso")
    @GetMapping
    public ResponseEntity<List<Organizador>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Buscar organizador por ID", description = "Recupera um organizador específico pelo seu UUID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Organizador encontrado"),
        @ApiResponse(responseCode = "404", description = "Organizador não encontrado (formato RFC 9457)")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Organizador> buscar(
            @Parameter(description = "UUID do organizador", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar organizador",
            description = "Cria um novo organizador. O e-mail deve ser único entre todos os usuários.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Organizador criado; o endereço do novo recurso vem no cabeçalho Location"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos no corpo da requisição (formato RFC 9457)"),
        @ApiResponse(responseCode = "409", description = "Já existe um usuário com esse e-mail (formato RFC 9457)")
    })
    @PostMapping
    public ResponseEntity<Void> criar(@Valid @RequestBody OrganizadorRequest request) {
        Organizador organizador = service.criar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(organizador.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Atualizar organizador",
            description = "Substitui os dados de um organizador existente. Senha em branco mantém a atual.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Organizador atualizado com sucesso (sem conteúdo no retorno)"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos no corpo da requisição (formato RFC 9457)"),
        @ApiResponse(responseCode = "404", description = "Organizador não encontrado (formato RFC 9457)"),
        @ApiResponse(responseCode = "409", description = "Já existe outro usuário com esse e-mail (formato RFC 9457)")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @Parameter(description = "UUID do organizador") @PathVariable UUID id,
            @Valid @RequestBody OrganizadorRequest request) {
        service.atualizar(id, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remover organizador", description = "Exclui um organizador do sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Organizador removido com sucesso (sem conteúdo no retorno)"),
        @ApiResponse(responseCode = "404", description = "Organizador não encontrado (formato RFC 9457)")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@Parameter(description = "UUID do organizador") @PathVariable UUID id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

}
