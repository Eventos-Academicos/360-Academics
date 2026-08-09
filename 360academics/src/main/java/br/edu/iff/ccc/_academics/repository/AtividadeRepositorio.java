package br.edu.iff.ccc._academics.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc._academics.entities.Atividade;

@Repository
public class AtividadeRepositorio {

    private final List<Atividade> atividades = new ArrayList<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public Atividade salvar(Atividade atividade) {
        atividade.setId(proximoId.getAndIncrement());
        this.atividades.add(atividade);
        return atividade;
    }

    public List<Atividade> listarTodos() {
        return this.atividades;
    }

    public List<Atividade> listarPorEvento(Long eventoId) {
        return this.atividades.stream()
                .filter(a -> a.getEvento() != null && a.getEvento().getId().equals(eventoId))
                .toList();
    }

    public Atividade buscarPorId(Long id) {
        for (Atividade atividade : this.atividades) {
            if (atividade.getId() != null && atividade.getId().equals(id)) {
                return atividade;
            }
        }
        return null;
    }

    public void remover(Atividade atividade) {
        this.atividades.remove(atividade);
    }

}
