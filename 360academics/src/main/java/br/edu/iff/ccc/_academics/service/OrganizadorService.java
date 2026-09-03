package br.edu.iff.ccc._academics.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.dto.OrganizadorRequest;
import br.edu.iff.ccc._academics.entities.Organizador;
import br.edu.iff.ccc._academics.exception.EntidadeDuplicadaException;
import br.edu.iff.ccc._academics.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc._academics.repository.OrganizadorRepositorio;
import br.edu.iff.ccc._academics.repository.UsuarioRepositorio;

@Service
public class OrganizadorService {

    private final OrganizadorRepositorio repositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    public OrganizadorService(OrganizadorRepositorio repositorio, UsuarioRepositorio usuarioRepositorio) {
        this.repositorio = repositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public List<Organizador> listarTodos() {
        return repositorio.findAll();
    }

    public Organizador buscarPorId(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Organizador não encontrado."));
    }

    public Organizador criar(OrganizadorRequest request) {
        if (usuarioRepositorio.existsByEmail(request.getEmail())) {
            throw new EntidadeDuplicadaException("Já existe um usuário cadastrado com o e-mail "
                    + request.getEmail() + ".");
        }
        Organizador organizador = new Organizador();
        organizador.setNome(request.getNome());
        organizador.setEmail(request.getEmail());
        organizador.setSenha(request.getSenha());
        return repositorio.save(organizador);
    }

    public Organizador atualizar(UUID id, OrganizadorRequest request) {
        Organizador organizador = buscarPorId(id);
        if (usuarioRepositorio.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new EntidadeDuplicadaException("Já existe outro usuário cadastrado com o e-mail "
                    + request.getEmail() + ".");
        }
        organizador.setNome(request.getNome());
        organizador.setEmail(request.getEmail());
        if (request.getSenha() != null && !request.getSenha().isBlank()) {
            organizador.setSenha(request.getSenha());
        }
        return repositorio.save(organizador);
    }

    public void remover(UUID id) {
        repositorio.deleteById(id);
    }

}
