package br.edu.iff.ccc._academics.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.dto.ParticipanteRequest;
import br.edu.iff.ccc._academics.entities.Participante;
import br.edu.iff.ccc._academics.exception.EntidadeDuplicadaException;
import br.edu.iff.ccc._academics.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc._academics.repository.ParticipanteRepositorio;
import br.edu.iff.ccc._academics.repository.UsuarioRepositorio;

@Service
public class ParticipanteService {

    private final ParticipanteRepositorio repositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    public ParticipanteService(ParticipanteRepositorio repositorio, UsuarioRepositorio usuarioRepositorio) {
        this.repositorio = repositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public List<Participante> listarTodos() {
        return repositorio.findAll();
    }

    public Participante buscarPorId(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Participante não encontrado."));
    }

    public Participante criar(ParticipanteRequest request) {
        if (usuarioRepositorio.existsByEmail(request.getEmail())) {
            throw new EntidadeDuplicadaException("Já existe um usuário cadastrado com o e-mail "
                    + request.getEmail() + ".");
        }
        Participante participante = new Participante();
        participante.setNome(request.getNome());
        participante.setEmail(request.getEmail());
        participante.setSenha(request.getSenha());
        participante.setMatricula(request.getMatricula());
        return repositorio.save(participante);
    }

    public Participante atualizar(UUID id, ParticipanteRequest request) {
        Participante participante = buscarPorId(id);
        if (usuarioRepositorio.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new EntidadeDuplicadaException("Já existe outro usuário cadastrado com o e-mail "
                    + request.getEmail() + ".");
        }
        participante.setNome(request.getNome());
        participante.setEmail(request.getEmail());
        participante.setMatricula(request.getMatricula());
        return repositorio.save(participante);
    }

    public void remover(UUID id) {
        repositorio.deleteById(id);
    }

}
