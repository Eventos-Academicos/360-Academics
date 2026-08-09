package br.edu.iff.ccc._academics.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc._academics.entities.Presenca;

@Repository
public class PresencaRepositorio {

    private final List<Presenca> presencas = new ArrayList<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public Presenca salvar(Presenca presenca) {
        presenca.setId(proximoId.getAndIncrement());
        this.presencas.add(presenca);
        return presenca;
    }

    public Presenca buscarPorInscricaoEAtividade(Long inscricaoId, Long atividadeId) {
        for (Presenca presenca : this.presencas) {
            if (presenca.getInscricao().getId().equals(inscricaoId)
                    && presenca.getAtividade().getId().equals(atividadeId)) {
                return presenca;
            }
        }
        return null;
    }

    public List<Presenca> listarConfirmadasPorParticipante(Long participanteId) {
        return this.presencas.stream()
                .filter(Presenca::isConfirmada)
                .filter(p -> p.getInscricao().getParticipante().getId().equals(participanteId))
                .toList();
    }

    public void remover(Presenca presenca) {
        this.presencas.remove(presenca);
    }

}
