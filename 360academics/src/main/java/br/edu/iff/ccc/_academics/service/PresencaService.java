package br.edu.iff.ccc._academics.service;

import java.util.ArrayList;
import java.util.List;

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

    public List<Presenca> listarPorAtividade(Long atividadeId) {
        Atividade atividade = atividadeService.buscarPorId(atividadeId);
        List<Inscricao> inscricoes = inscricaoService.listarPorEvento(atividade.getEvento().getId());

        List<Presenca> resultado = new ArrayList<>();
        for (Inscricao inscricao : inscricoes) {
            Presenca presenca = repositorio.buscarPorInscricaoEAtividade(inscricao.getId(), atividadeId);
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

    public boolean estaConfirmada(Long inscricaoId, Long atividadeId) {
        Presenca presenca = repositorio.buscarPorInscricaoEAtividade(inscricaoId, atividadeId);
        return presenca != null && presenca.isConfirmada();
    }

    public void confirmar(Long atividadeId, Long inscricaoId) {
        Atividade atividade = atividadeService.buscarPorId(atividadeId);
        Inscricao inscricao = inscricaoService.buscarPorId(inscricaoId);

        boolean conflitaHorario = repositorio.listarConfirmadasPorParticipante(inscricao.getParticipante().getId())
                .stream()
                .filter(p -> !p.getAtividade().getId().equals(atividadeId))
                .anyMatch(p -> sobrepoe(p.getAtividade(), atividade));
        if (conflitaHorario) {
            throw new RegraNegocioException(
                    "Participante já possui presença confirmada em outra atividade no mesmo horário.");
        }

        Presenca presenca = repositorio.buscarPorInscricaoEAtividade(inscricaoId, atividadeId);
        if (presenca == null) {
            presenca = new Presenca();
            presenca.setAtividade(atividade);
            presenca.setInscricao(inscricao);
            presenca.setConfirmada(true);
            repositorio.salvar(presenca);
        } else {
            presenca.setConfirmada(true);
        }
    }

    private boolean sobrepoe(Atividade a, Atividade b) {
        return a.getHorarioInicio().isBefore(b.getHorarioFim()) && b.getHorarioInicio().isBefore(a.getHorarioFim());
    }

    public void desconfirmar(Long atividadeId, Long inscricaoId) {
        Presenca presenca = repositorio.buscarPorInscricaoEAtividade(inscricaoId, atividadeId);
        if (presenca != null) {
            repositorio.remover(presenca);
        }
    }

}
