package br.edu.iff.ccc._academics.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.dto.ParticipanteRequest;
import br.edu.iff.ccc._academics.entities.Participante;
import br.edu.iff.ccc._academics.repository.ParticipanteRepositorio;

@Service
public class ParticipanteService {

    private final ParticipanteRepositorio repositorio;

    public ParticipanteService(ParticipanteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<Participante> listarTodos() {
        return repositorio.listarTodos();
    }

    public Participante buscarPorId(Long id) {
        return repositorio.buscarPorId(id);
    }

    public Participante criar(ParticipanteRequest request) {
        Participante participante = new Participante();
        participante.setNome(request.getNome());
        participante.setEmail(request.getEmail());
        participante.setSenha(request.getSenha());
        participante.setMatricula(request.getMatricula());
        return repositorio.salvar(participante);
    }

    public Participante atualizar(Long id, ParticipanteRequest request) {
        Participante participante = repositorio.buscarPorId(id);
        if (participante != null) {
            participante.setNome(request.getNome());
            participante.setEmail(request.getEmail());
            participante.setMatricula(request.getMatricula());
        }
        return participante;
    }

    public void remover(Long id) {
        Participante participante = repositorio.buscarPorId(id);
        if (participante != null) {
            repositorio.remover(participante);
        }
    }

}
