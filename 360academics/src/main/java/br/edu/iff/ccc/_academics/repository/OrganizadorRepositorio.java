package br.edu.iff.ccc._academics.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc._academics.entities.Organizador;

@Repository
public class OrganizadorRepositorio {

    private final List<Organizador> organizadores = new ArrayList<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public Organizador salvar(Organizador organizador) {
        organizador.setId(proximoId.getAndIncrement());
        this.organizadores.add(organizador);
        return organizador;
    }

    public List<Organizador> listarTodos() {
        return this.organizadores;
    }

    public Organizador buscarPorId(Long id) {
        for (Organizador organizador : this.organizadores) {
            if (organizador.getId() != null && organizador.getId().equals(id)) {
                return organizador;
            }
        }
        return null;
    }

    public void remover(Organizador organizador) {
        this.organizadores.remove(organizador);
    }

}
