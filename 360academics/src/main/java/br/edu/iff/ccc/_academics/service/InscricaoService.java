package br.edu.iff.ccc._academics.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.dto.InscricaoRequest;
import br.edu.iff.ccc._academics.entities.Evento;
import br.edu.iff.ccc._academics.entities.Inscricao;
import br.edu.iff.ccc._academics.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc._academics.exception.RegraNegocioException;
import br.edu.iff.ccc._academics.repository.InscricaoRepositorio;

@Service
public class InscricaoService {

    private final InscricaoRepositorio repositorio;
    private final EventoService eventoService;
    private final ParticipanteService participanteService;

    public InscricaoService(InscricaoRepositorio repositorio, EventoService eventoService,
            ParticipanteService participanteService) {
        this.repositorio = repositorio;
        this.eventoService = eventoService;
        this.participanteService = participanteService;
    }

    public List<Inscricao> listarTodos() {
        return repositorio.findAll();
    }

    public List<Inscricao> listarPorEvento(UUID eventoId) {
        return repositorio.findByEventoId(eventoId);
    }

    public Inscricao buscarPorId(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Inscrição não encontrada."));
    }

    public Inscricao criar(UUID eventoId, InscricaoRequest request) {
        Evento evento = eventoService.buscarPorId(eventoId);

        long vagasOcupadas = repositorio.findByEventoId(eventoId).stream()
                .filter(i -> !"Cancelada".equals(i.getStatus()))
                .count();
        if (vagasOcupadas >= evento.getLimiteVagas()) {
            throw new RegraNegocioException("Limite de vagas do evento \"" + evento.getNome() + "\" atingido.");
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setEvento(evento);
        inscricao.setParticipante(participanteService.buscarPorId(request.getParticipanteId()));
        inscricao.setDataInscricao(LocalDate.now());
        inscricao.setStatus("Confirmada");
        return repositorio.save(inscricao);
    }

    public Inscricao atualizarStatus(UUID id, String status) {
        Inscricao inscricao = buscarPorId(id);
        inscricao.setStatus(status);
        return repositorio.save(inscricao);
    }

    public void remover(UUID id) {
        repositorio.deleteById(id);
    }

}
