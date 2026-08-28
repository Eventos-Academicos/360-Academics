package br.edu.iff.ccc._academics.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.dto.ParticipanteRequest;
import br.edu.iff.ccc._academics.entities.Participante;
import br.edu.iff.ccc._academics.exception.RegraNegocioException;
import br.edu.iff.ccc._academics.repository.ParticipanteRepositorio;

@Service
public class ParticipanteService {

    private final ParticipanteRepositorio repositorio;

    public ParticipanteService(ParticipanteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<Participante> listarTodos() {
        return repositorio.findAll();
    }

    public Participante buscarPorId(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Participante não encontrado."));
    }

    public Participante criar(ParticipanteRequest request) {
        Participante participante = new Participante();
        participante.setNome(request.getNome());
        participante.setEmail(request.getEmail());
        participante.setSenha(request.getSenha());
        participante.setMatricula(request.getMatricula());
        return repositorio.save(participante);
    }

    public Participante atualizar(UUID id, ParticipanteRequest request) {
        Participante participante = buscarPorId(id);
        participante.setNome(request.getNome());
        participante.setEmail(request.getEmail());
        participante.setMatricula(request.getMatricula());
        return repositorio.save(participante);
    }

    public void remover(UUID id) {
        repositorio.deleteById(id);
    }

}
