package br.edu.iff.ccc._academics.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc._academics.entities.HistoricoParticipacao;

@Repository
public class HistoricoRepositorio {

    private final List<HistoricoParticipacao> historico = new ArrayList<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public HistoricoParticipacao salvar(HistoricoParticipacao item) {
        item.setId(proximoId.getAndIncrement());
        this.historico.add(item);
        return item;
    }

    public List<HistoricoParticipacao> listarTodos() {
        return this.historico;
    }

    public HistoricoParticipacao buscarPorId(Long id) {
        for (HistoricoParticipacao item : this.historico) {
            if (item.getId() != null && item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public HistoricoParticipacao buscarPorParticipanteEEvento(Long participanteId, Long eventoId) {
        for (HistoricoParticipacao item : this.historico) {
            if (item.getParticipante().getId().equals(participanteId)
                    && item.getEvento().getId().equals(eventoId)) {
                return item;
            }
        }
        return null;
    }

    public void remover(HistoricoParticipacao item) {
        this.historico.remove(item);
    }

}
