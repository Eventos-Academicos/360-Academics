package br.edu.iff.ccc._academics.controller.rest;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.iff.ccc._academics.dto.ConfirmacaoCertificadoRequest;
import br.edu.iff.ccc._academics.entities.Certificado;
import br.edu.iff.ccc._academics.service.CertificadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/certificados")
@Tag(name = "Certificados", description = "Confirmação e consulta dos certificados dos participantes")
public class CertificadoRestController {

    private final CertificadoService service;

    public CertificadoRestController(CertificadoService service) {
        this.service = service;
    }

    @Operation(summary = "Confirmar emissão de certificado",
            description = "O organizador confirma a emissão do certificado de um participante. "
                    + "A presença do participante na atividade precisa estar confirmada.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Emissão confirmada; o endereço do certificado vem no cabeçalho Location"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos no corpo da requisição (formato RFC 9457)"),
        @ApiResponse(responseCode = "404", description = "Organizador, inscrição ou atividade não encontrados (formato RFC 9457)"),
        @ApiResponse(responseCode = "409", description = "Emissão já confirmada anteriormente (formato RFC 9457)"),
        @ApiResponse(responseCode = "422", description = "Presença do participante não confirmada (formato RFC 9457)")
    })
    @PostMapping("/confirmacoes")
    public ResponseEntity<Void> confirmarEmissao(@Valid @RequestBody ConfirmacaoCertificadoRequest request) {
        Certificado certificado = service.confirmarEmissao(request);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/certificados/{id}").buildAndExpand(certificado.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Buscar certificado por ID",
            description = "Recupera um certificado específico pelo seu UUID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Certificado encontrado"),
        @ApiResponse(responseCode = "404", description = "Certificado não encontrado (formato RFC 9457)")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Certificado> buscar(
            @Parameter(description = "UUID do certificado", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

}
