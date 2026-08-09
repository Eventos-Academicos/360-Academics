package br.edu.iff.ccc._academics.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc._academics.entities.Evento;

@Repository
public class EventoRepositorio {

    private final List<Evento> eventos = new ArrayList<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public Evento salvar(Evento evento) {
        evento.setId(proximoId.getAndIncrement());
        this.eventos.add(evento);
        return evento;
    }

    public List<Evento> listarTodos() {
        return this.eventos;
    }

    public Evento buscarPorId(Long id) {
        for (Evento evento : this.eventos) {
            if (evento.getId() != null && evento.getId().equals(id)) {
                return evento;
            }
        }
        return null;
    }

    public void remover(Evento evento) {
        this.eventos.remove(evento);
    }

}
