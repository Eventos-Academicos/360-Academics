package br.edu.iff.ccc._academics.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.dto.EventoRequest;
import br.edu.iff.ccc._academics.entities.Evento;
import br.edu.iff.ccc._academics.exception.RegraNegocioException;
import br.edu.iff.ccc._academics.repository.EventoRepositorio;

@Service
public class EventoService {

    private final EventoRepositorio repositorio;

    public EventoService(EventoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<Evento> listarTodos() {
        return repositorio.findAll();
    }

    public Evento buscarPorId(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Evento não encontrado."));
    }

    public Evento criar(EventoRequest request) {
        Evento evento = new Evento();
        preencher(evento, request);
        return repositorio.save(evento);
    }

    public Evento atualizar(UUID id, EventoRequest request) {
        Evento evento = buscarPorId(id);
        preencher(evento, request);
        return repositorio.save(evento);
    }

    public void remover(UUID id) {
        repositorio.deleteById(id);
    }

    private void preencher(Evento evento, EventoRequest request) {
        evento.setNome(request.getNome());
        evento.setDescricao(request.getDescricao());
        evento.setLocal(request.getLocal());
        evento.setDataInicio(request.getDataInicio());
        evento.setDataFim(request.getDataFim());
        evento.setLimiteVagas(request.getLimiteVagas());
    }

}
