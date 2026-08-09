package br.edu.iff.ccc._academics.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.dto.OrganizadorRequest;
import br.edu.iff.ccc._academics.entities.Organizador;
import br.edu.iff.ccc._academics.repository.OrganizadorRepositorio;

@Service
public class OrganizadorService {

    private final OrganizadorRepositorio repositorio;

    public OrganizadorService(OrganizadorRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<Organizador> listarTodos() {
        return repositorio.listarTodos();
    }

    public Organizador buscarPorId(Long id) {
        return repositorio.buscarPorId(id);
    }

    public Organizador criar(OrganizadorRequest request) {
        Organizador organizador = new Organizador();
        organizador.setNome(request.getNome());
        organizador.setEmail(request.getEmail());
        organizador.setSenha(request.getSenha());
        return repositorio.salvar(organizador);
    }

    public Organizador atualizar(Long id, OrganizadorRequest request) {
        Organizador organizador = repositorio.buscarPorId(id);
        if (organizador != null) {
            organizador.setNome(request.getNome());
            organizador.setEmail(request.getEmail());
            if (request.getSenha() != null && !request.getSenha().isBlank()) {
                organizador.setSenha(request.getSenha());
            }
        }
        return organizador;
    }

    public void remover(Long id) {
        Organizador organizador = repositorio.buscarPorId(id);
        if (organizador != null) {
            repositorio.remover(organizador);
        }
    }

}
