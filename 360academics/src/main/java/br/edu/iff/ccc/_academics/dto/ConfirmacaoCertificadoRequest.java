package br.edu.iff.ccc._academics.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para o organizador confirmar a emissão do certificado de um participante")
public record ConfirmacaoCertificadoRequest(

        @NotNull(message = "A inscrição é obrigatória.")
        UUID inscricaoId,

        @NotNull(message = "A atividade é obrigatória.")
        UUID atividadeId,

        @NotNull(message = "O organizador é obrigatório.")
        UUID organizadorId) {
}
