package br.edu.iff.ccc._academics.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc._academics.entities.Inscricao;

@Repository
public class InscricaoRepositorio {

    private final List<Inscricao> inscricoes = new ArrayList<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public Inscricao salvar(Inscricao inscricao) {
        inscricao.setId(proximoId.getAndIncrement());
        this.inscricoes.add(inscricao);
        return inscricao;
    }

    public List<Inscricao> listarTodos() {
        return this.inscricoes;
    }

    public List<Inscricao> listarPorEvento(Long eventoId) {
        return this.inscricoes.stream()
                .filter(i -> i.getEvento() != null && i.getEvento().getId().equals(eventoId))
                .toList();
    }

    public List<Inscricao> listarPorParticipante(Long participanteId) {
        return this.inscricoes.stream()
                .filter(i -> i.getParticipante() != null && i.getParticipante().getId().equals(participanteId))
                .toList();
    }

    public Inscricao buscarPorId(Long id) {
        for (Inscricao inscricao : this.inscricoes) {
            if (inscricao.getId() != null && inscricao.getId().equals(id)) {
                return inscricao;
            }
        }
        return null;
    }

    public void remover(Inscricao inscricao) {
        this.inscricoes.remove(inscricao);
    }

}
