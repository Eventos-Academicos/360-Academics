package br.edu.iff.ccc._academics.dto;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para registro da conclusão de um evento por um participante")
public class HistoricoRequest {

    @Schema(description = "UUID do participante que concluiu o evento",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Selecione o participante.")
    private UUID participanteId;

    @Schema(description = "UUID do evento concluído",
            example = "7c9e6679-7425-40de-944b-e07fc1f90ae7",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Selecione o evento.")
    private UUID eventoId;

    @Schema(description = "Data em que o participante concluiu o evento", example = "2026-10-09",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A data de conclusão é obrigatória.")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataConclusao;

    public UUID getParticipanteId() {
        return participanteId;
    }

    public void setParticipanteId(UUID participanteId) {
        this.participanteId = participanteId;
    }

    public UUID getEventoId() {
        return eventoId;
    }

    public void setEventoId(UUID eventoId) {
        this.eventoId = eventoId;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

}
