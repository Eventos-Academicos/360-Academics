package br.edu.iff.ccc._academics.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.dto.AtividadeRequest;
import br.edu.iff.ccc._academics.entities.Atividade;
import br.edu.iff.ccc._academics.entities.Palestrante;
import br.edu.iff.ccc._academics.repository.AtividadeRepositorio;

@Service
public class AtividadeService {

    private final AtividadeRepositorio repositorio;
    private final EventoService eventoService;
    private final PalestranteService palestranteService;

    public AtividadeService(AtividadeRepositorio repositorio, EventoService eventoService,
            PalestranteService palestranteService) {
        this.repositorio = repositorio;
        this.eventoService = eventoService;
        this.palestranteService = palestranteService;
    }

    public List<Atividade> listarPorEvento(Long eventoId) {
        return repositorio.listarPorEvento(eventoId);
    }

    public Atividade buscarPorId(Long id) {
        return repositorio.buscarPorId(id);
    }

    public Atividade criar(Long eventoId, AtividadeRequest request) {
        Atividade atividade = new Atividade();
        atividade.setEvento(eventoService.buscarPorId(eventoId));
        preencher(atividade, request);
        return repositorio.salvar(atividade);
    }

    public Atividade atualizar(Long id, AtividadeRequest request) {
        Atividade atividade = repositorio.buscarPorId(id);
        if (atividade != null) {
            preencher(atividade, request);
        }
        return atividade;
    }

    public void remover(Long id) {
        Atividade atividade = repositorio.buscarPorId(id);
        if (atividade != null) {
            repositorio.remover(atividade);
        }
    }

    private void preencher(Atividade atividade, AtividadeRequest request) {
        atividade.setTitulo(request.getTitulo());
        atividade.setHorarioInicio(request.getHorarioInicio());
        atividade.setHorarioFim(request.getHorarioFim());
        atividade.setLocal(request.getLocal());
        Palestrante palestrante = palestranteService.buscarPorId(request.getPalestranteId());
        atividade.setPalestrante(palestrante);
    }

}
