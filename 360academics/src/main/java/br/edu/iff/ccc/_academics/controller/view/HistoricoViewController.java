package br.edu.iff.ccc._academics.controller.view;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc._academics.dto.HistoricoRequest;
import br.edu.iff.ccc._academics.entities.HistoricoParticipacao;
import br.edu.iff.ccc._academics.service.EventoService;
import br.edu.iff.ccc._academics.service.HistoricoService;
import br.edu.iff.ccc._academics.service.ParticipanteService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/historico")
public class HistoricoViewController {

    private final HistoricoService service;
    private final ParticipanteService participanteService;
    private final EventoService eventoService;

    public HistoricoViewController(HistoricoService service, ParticipanteService participanteService,
            EventoService eventoService) {
        this.service = service;
        this.participanteService = participanteService;
        this.eventoService = eventoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Histórico de Participação");
        model.addAttribute("historico", service.listarTodos());
        return "historico/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("titulo", "Registrar Participação Concluída");
        model.addAttribute("historicoRequest", new HistoricoRequest());
        adicionarListasDeSelecao(model);
        return "historico/form";
    }

    @PostMapping
    public String criar(@Valid HistoricoRequest historicoRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Registrar Participação Concluída");
            adicionarListasDeSelecao(model);
            return "historico/form";
        }
        service.criar(historicoRequest);
        return "redirect:/historico";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable UUID id, Model model) {
        model.addAttribute("titulo", "Editar Participação");
        model.addAttribute("id", id);
        model.addAttribute("historicoRequest", paraRequest(service.buscarPorId(id)));
        adicionarListasDeSelecao(model);
        return "historico/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable UUID id, @Valid HistoricoRequest historicoRequest,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Editar Participação");
            model.addAttribute("id", id);
            adicionarListasDeSelecao(model);
            return "historico/form";
        }
        service.atualizar(id, historicoRequest);
        return "redirect:/historico";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable UUID id) {
        service.remover(id);
        return "redirect:/historico";
    }

    private void adicionarListasDeSelecao(Model model) {
        model.addAttribute("participantes", participanteService.listarTodos());
        model.addAttribute("eventos", eventoService.listarTodos());
    }

    private HistoricoRequest paraRequest(HistoricoParticipacao item) {
        HistoricoRequest request = new HistoricoRequest();
        request.setParticipanteId(item.getParticipante().getId());
        request.setEventoId(item.getEvento().getId());
        request.setDataConclusao(item.getDataConclusao());
        return request;
    }

}
