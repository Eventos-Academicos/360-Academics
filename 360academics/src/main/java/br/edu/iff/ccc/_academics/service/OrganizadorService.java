package br.edu.iff.ccc._academics.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.dto.OrganizadorRequest;
import br.edu.iff.ccc._academics.entities.Organizador;
import br.edu.iff.ccc._academics.exception.RegraNegocioException;
import br.edu.iff.ccc._academics.repository.OrganizadorRepositorio;

@Service
public class OrganizadorService {

    private final OrganizadorRepositorio repositorio;

    public OrganizadorService(OrganizadorRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<Organizador> listarTodos() {
        return repositorio.findAll();
    }

    public Organizador buscarPorId(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Organizador não encontrado."));
    }

    public Organizador criar(OrganizadorRequest request) {
        Organizador organizador = new Organizador();
        organizador.setNome(request.getNome());
        organizador.setEmail(request.getEmail());
        organizador.setSenha(request.getSenha());
        return repositorio.save(organizador);
    }

    public Organizador atualizar(UUID id, OrganizadorRequest request) {
        Organizador organizador = buscarPorId(id);
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
