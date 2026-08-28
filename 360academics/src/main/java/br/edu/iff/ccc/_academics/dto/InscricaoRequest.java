package br.edu.iff.ccc._academics.dto;

import java.util.UUID;

public class InscricaoRequest {

    private UUID participanteId;

    public UUID getParticipanteId() {
        return participanteId;
    }

    public void setParticipanteId(UUID participanteId) {
        this.participanteId = participanteId;
    }

}
