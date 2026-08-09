package br.edu.iff.ccc._academics.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.dto.InscricaoRequest;
import br.edu.iff.ccc._academics.entities.Evento;
import br.edu.iff.ccc._academics.entities.Inscricao;
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
        return repositorio.listarTodos();
    }

    public List<Inscricao> listarPorEvento(Long eventoId) {
        return repositorio.listarPorEvento(eventoId);
    }

    public Inscricao buscarPorId(Long id) {
        return repositorio.buscarPorId(id);
    }

    public Inscricao criar(Long eventoId, InscricaoRequest request) {
        Evento evento = eventoService.buscarPorId(eventoId);

        long vagasOcupadas = repositorio.listarPorEvento(eventoId).stream()
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
        return repositorio.salvar(inscricao);
    }

    public Inscricao atualizarStatus(Long id, String status) {
        Inscricao inscricao = repositorio.buscarPorId(id);
        if (inscricao != null) {
            inscricao.setStatus(status);
        }
        return inscricao;
    }

    public void remover(Long id) {
        Inscricao inscricao = repositorio.buscarPorId(id);
        if (inscricao != null) {
            repositorio.remover(inscricao);
        }
    }

}
