package br.edu.iff.ccc._academics.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.dto.PalestranteRequest;
import br.edu.iff.ccc._academics.entities.Palestrante;
import br.edu.iff.ccc._academics.exception.EntidadeDuplicadaException;
import br.edu.iff.ccc._academics.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc._academics.repository.PalestranteRepositorio;
import br.edu.iff.ccc._academics.repository.UsuarioRepositorio;

@Service
public class PalestranteService {

    private final PalestranteRepositorio repositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    public PalestranteService(PalestranteRepositorio repositorio, UsuarioRepositorio usuarioRepositorio) {
        this.repositorio = repositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public List<Palestrante> listarTodos() {
        return repositorio.findAll();
    }

    public Palestrante buscarPorId(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Palestrante não encontrado."));
    }

    public Palestrante criar(PalestranteRequest request) {
        if (usuarioRepositorio.existsByEmail(request.getEmail())) {
            throw new EntidadeDuplicadaException("Já existe um usuário cadastrado com o e-mail "
                    + request.getEmail() + ".");
        }
        Palestrante palestrante = new Palestrante();
        preencher(palestrante, request);
        palestrante.setSenha(request.getSenha());
        return repositorio.save(palestrante);
    }

    public Palestrante atualizar(UUID id, PalestranteRequest request) {
        Palestrante palestrante = buscarPorId(id);
        if (usuarioRepositorio.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new EntidadeDuplicadaException("Já existe outro usuário cadastrado com o e-mail "
                    + request.getEmail() + ".");
        }
        preencher(palestrante, request);
        if (request.getSenha() != null && !request.getSenha().isBlank()) {
            palestrante.setSenha(request.getSenha());
        }
        return repositorio.save(palestrante);
    }

    public void remover(UUID id) {
        repositorio.deleteById(id);
    }

    private void preencher(Palestrante palestrante, PalestranteRequest request) {
        palestrante.setNome(request.getNome());
        palestrante.setEmail(request.getEmail());
        palestrante.setEspecialidade(request.getEspecialidade());
    }

}
