package br.edu.iff.ccc._academics.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc._academics.entities.Participante;

@Repository
public class ParticipanteRepositorio {

    private final List<Participante> participantes = new ArrayList<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public Participante salvar(Participante participante) {
        participante.setId(proximoId.getAndIncrement());
        this.participantes.add(participante);
        return participante;
    }

    public List<Participante> listarTodos() {
        return this.participantes;
    }

    public Participante buscarPorId(Long id) {
        for (Participante participante : this.participantes) {
            if (participante.getId() != null && participante.getId().equals(id)) {
                return participante;
            }
        }
        return null;
    }

    public void remover(Participante participante) {
        this.participantes.remove(participante);
    }

}