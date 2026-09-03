package br.edu.iff.ccc._academics.dto;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.validation.constraints.NotNull;

public class HistoricoRequest {

    @NotNull(message = "Selecione o participante.")
    private UUID participanteId;

    @NotNull(message = "Selecione o evento.")
    private UUID eventoId;

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
