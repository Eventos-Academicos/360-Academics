package br.edu.iff.ccc._academics.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc._academics.entities.Palestrante;

@Repository
public class PalestranteRepositorio {

    private final List<Palestrante> palestrantes = new ArrayList<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public Palestrante salvar(Palestrante palestrante) {
        palestrante.setId(proximoId.getAndIncrement());
        this.palestrantes.add(palestrante);
        return palestrante;
    }

    public List<Palestrante> listarTodos() {
        return this.palestrantes;
    }

    public Palestrante buscarPorId(Long id) {
        for (Palestrante palestrante : this.palestrantes) {
            if (palestrante.getId() != null && palestrante.getId().equals(id)) {
                return palestrante;
            }
        }
        return null;
    }

    public void remover(Palestrante palestrante) {
        this.palestrantes.remove(palestrante);
    }

}
