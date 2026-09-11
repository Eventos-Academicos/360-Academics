package br.edu.iff.ccc._academics.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.dto.AtividadeRequest;
import br.edu.iff.ccc._academics.entities.Atividade;
import br.edu.iff.ccc._academics.entities.Palestrante;
import br.edu.iff.ccc._academics.exception.RecursoNaoEncontradoException;
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

    public List<Atividade> listarPorEvento(UUID eventoId) {
        return repositorio.findByEventoId(eventoId);
    }

    public Atividade buscarPorId(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Atividade não encontrada."));
    }

    public Atividade criar(UUID eventoId, AtividadeRequest request) {
        Atividade atividade = new Atividade();
        atividade.setEvento(eventoService.buscarPorId(eventoId));
        preencher(atividade, request);
        return repositorio.save(atividade);
    }

    public Atividade atualizar(UUID id, AtividadeRequest request) {
        Atividade atividade = buscarPorId(id);
        preencher(atividade, request);
        return repositorio.save(atividade);
    }

    public void remover(UUID id) {
        buscarPorId(id); // lança RecursoNaoEncontradoException se não existir
        repositorio.deleteById(id);
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
