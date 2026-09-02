package br.edu.iff.ccc._academics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.entities.Atividade;
import br.edu.iff.ccc._academics.entities.Inscricao;
import br.edu.iff.ccc._academics.entities.Presenca;
import br.edu.iff.ccc._academics.exception.RegraNegocioException;
import br.edu.iff.ccc._academics.repository.PresencaRepositorio;

@Service
public class PresencaService {

    private final PresencaRepositorio repositorio;
    private final AtividadeService atividadeService;
    private final InscricaoService inscricaoService;

    public PresencaService(PresencaRepositorio repositorio, AtividadeService atividadeService,
            InscricaoService inscricaoService) {
        this.repositorio = repositorio;
        this.atividadeService = atividadeService;
        this.inscricaoService = inscricaoService;
    }

    public List<Presenca> listarPorAtividade(UUID atividadeId) {
        Atividade atividade = atividadeService.buscarPorId(atividadeId);
        List<Inscricao> inscricoes = inscricaoService.listarPorEvento(atividade.getEvento().getId());

        List<Presenca> resultado = new ArrayList<>();
        for (Inscricao inscricao : inscricoes) {
            Presenca presenca = repositorio
                    .findByInscricaoIdAndAtividadeId(inscricao.getId(), atividadeId)
                    .orElse(null);
            if (presenca == null) {
                presenca = new Presenca();
                presenca.setInscricao(inscricao);
                presenca.setAtividade(atividade);
                presenca.setConfirmada(false);
            }
            resultado.add(presenca);
        }
        return resultado;
    }

    public boolean estaConfirmada(UUID inscricaoId, UUID atividadeId) {
        return repositorio.findByInscricaoIdAndAtividadeId(inscricaoId, atividadeId)
                .map(Presenca::isConfirmada)
                .orElse(false);
    }

    public void confirmar(UUID atividadeId, UUID inscricaoId) {
        Atividade atividade = atividadeService.buscarPorId(atividadeId);
        Inscricao inscricao = inscricaoService.buscarPorId(inscricaoId);

        boolean conflitaHorario = repositorio
                .findByConfirmadaTrueAndInscricao_Participante_Id(inscricao.getParticipante().getId())
                .stream()
                .filter(p -> !p.getAtividade().getId().equals(atividadeId))
                .anyMatch(p -> sobrepoe(p.getAtividade(), atividade));
        if (conflitaHorario) {
            throw new RegraNegocioException(
                    "Participante já possui presença confirmada em outra atividade no mesmo horário.");
        }

        Presenca presenca = repositorio.findByInscricaoIdAndAtividadeId(inscricaoId, atividadeId)
                .orElseGet(() -> {
                    Presenca nova = new Presenca();
                    nova.setAtividade(atividade);
                    nova.setInscricao(inscricao);
                    return nova;
                });
        presenca.setConfirmada(true);
        repositorio.save(presenca);
    }

    private boolean sobrepoe(Atividade a, Atividade b) {
        return a.getHorarioInicio().isBefore(b.getHorarioFim()) && b.getHorarioInicio().isBefore(a.getHorarioFim());
    }

    public void desconfirmar(UUID atividadeId, UUID inscricaoId) {
        repositorio.findByInscricaoIdAndAtividadeId(inscricaoId, atividadeId)
                .ifPresent(repositorio::delete);
    }

}
