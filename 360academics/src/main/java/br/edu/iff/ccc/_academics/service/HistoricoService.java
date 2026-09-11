package br.edu.iff.ccc._academics.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.dto.HistoricoRequest;
import br.edu.iff.ccc._academics.entities.Evento;
import br.edu.iff.ccc._academics.entities.HistoricoParticipacao;
import br.edu.iff.ccc._academics.entities.Participante;
import br.edu.iff.ccc._academics.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc._academics.repository.HistoricoRepositorio;

@Service
public class HistoricoService {

    private final HistoricoRepositorio repositorio;
    private final ParticipanteService participanteService;
    private final EventoService eventoService;

    public HistoricoService(HistoricoRepositorio repositorio, ParticipanteService participanteService,
            EventoService eventoService) {
        this.repositorio = repositorio;
        this.participanteService = participanteService;
        this.eventoService = eventoService;
    }

    public List<HistoricoParticipacao> listarTodos() {
        return repositorio.findAll();
    }

    public HistoricoParticipacao buscarPorId(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Registro de histórico não encontrado."));
    }

    public HistoricoParticipacao criar(HistoricoRequest request) {
        HistoricoParticipacao item = new HistoricoParticipacao();
        preencher(item, request);
        return repositorio.save(item);
    }

    public HistoricoParticipacao atualizar(UUID id, HistoricoRequest request) {
        HistoricoParticipacao item = buscarPorId(id);
        preencher(item, request);
        return repositorio.save(item);
    }

    public void remover(UUID id) {
        repositorio.deleteById(id);
    }

    public void registrarConclusao(Participante participante, Evento evento) {
        boolean jaRegistrado = repositorio
                .findByParticipanteIdAndEventoId(participante.getId(), evento.getId())
                .isPresent();
        if (!jaRegistrado) {
            HistoricoParticipacao item = new HistoricoParticipacao();
            item.setParticipante(participante);
            item.setEvento(evento);
            item.setDataConclusao(LocalDate.now());
            repositorio.save(item);
        }
    }

    private void preencher(HistoricoParticipacao item, HistoricoRequest request) {
        item.setParticipante(participanteService.buscarPorId(request.getParticipanteId()));
        item.setEvento(eventoService.buscarPorId(request.getEventoId()));
        item.setDataConclusao(request.getDataConclusao());
    }

}
