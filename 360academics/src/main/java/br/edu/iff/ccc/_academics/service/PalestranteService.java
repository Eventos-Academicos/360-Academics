package br.edu.iff.ccc._academics.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.dto.PalestranteRequest;
import br.edu.iff.ccc._academics.entities.Palestrante;
import br.edu.iff.ccc._academics.repository.PalestranteRepositorio;

@Service
public class PalestranteService {

    private final PalestranteRepositorio repositorio;

    public PalestranteService(PalestranteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<Palestrante> listarTodos() {
        return repositorio.listarTodos();
    }

    public Palestrante buscarPorId(Long id) {
        return repositorio.buscarPorId(id);
    }

    public Palestrante criar(PalestranteRequest request) {
        Palestrante palestrante = new Palestrante();
        preencher(palestrante, request);
        palestrante.setSenha(request.getSenha());
        return repositorio.salvar(palestrante);
    }

    public Palestrante atualizar(Long id, PalestranteRequest request) {
        Palestrante palestrante = repositorio.buscarPorId(id);
        if (palestrante != null) {
            preencher(palestrante, request);
            if (request.getSenha() != null && !request.getSenha().isBlank()) {
                palestrante.setSenha(request.getSenha());
            }
        }
        return palestrante;
    }

    public void remover(Long id) {
        Palestrante palestrante = repositorio.buscarPorId(id);
        if (palestrante != null) {
            repositorio.remover(palestrante);
        }
    }

    private void preencher(Palestrante palestrante, PalestranteRequest request) {
        palestrante.setNome(request.getNome());
        palestrante.setEmail(request.getEmail());
        palestrante.setEspecialidade(request.getEspecialidade());
    }

}
