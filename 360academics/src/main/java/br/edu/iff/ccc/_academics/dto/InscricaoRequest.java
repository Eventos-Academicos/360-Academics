package br.edu.iff.ccc._academics.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class InscricaoRequest {

    @NotNull(message = "O participante é obrigatório.")
    private UUID participanteId;

    public UUID getParticipanteId() {
        return participanteId;
    }

    public void setParticipanteId(UUID participanteId) {
        this.participanteId = participanteId;
    }

}
