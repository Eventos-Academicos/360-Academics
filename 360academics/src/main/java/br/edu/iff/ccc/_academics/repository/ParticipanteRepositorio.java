package br.edu.iff.ccc._academics.repository;

import java.util.ArrayList;
import java.util.List;

import br.edu.iff.ccc._academics.entities.Participante;

public class ParticipanteRepositorio {

    private List<Participante> participantes;

    public ParticipanteRepositorio() {
        this.participantes = new ArrayList<>();
    }

    public void salvar(Participante participante) {
        this.participantes.add(participante);
        System.out.println("Participante salvo: " + participante.getNome());
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