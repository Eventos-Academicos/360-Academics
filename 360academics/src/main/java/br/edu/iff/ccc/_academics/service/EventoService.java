package br.edu.iff.ccc._academics.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.dto.EventoRequest;
import br.edu.iff.ccc._academics.entities.Evento;
import br.edu.iff.ccc._academics.repository.EventoRepositorio;

@Service
public class EventoService {

    private final EventoRepositorio repositorio;

    public EventoService(EventoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<Evento> listarTodos() {
        return repositorio.listarTodos();
    }

    public Evento buscarPorId(Long id) {
        return repositorio.buscarPorId(id);
    }

    public Evento criar(EventoRequest request) {
        Evento evento = new Evento();
        preencher(evento, request);
        return repositorio.salvar(evento);
    }

    public Evento atualizar(Long id, EventoRequest request) {
        Evento evento = repositorio.buscarPorId(id);
        if (evento != null) {
            preencher(evento, request);
        }
        return evento;
    }

    public void remover(Long id) {
        Evento evento = repositorio.buscarPorId(id);
        if (evento != null) {
            repositorio.remover(evento);
        }
    }

    private void preencher(Evento evento, EventoRequest request) {
        evento.setNome(request.getNome());
        evento.setDescricao(request.getDescricao());
        evento.setDataInicio(request.getDataInicio());
        evento.setDataFim(request.getDataFim());
        evento.setLimiteVagas(request.getLimiteVagas());
    }

}
