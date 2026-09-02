package br.edu.iff.ccc._academics.controller.view;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc._academics.dto.AtividadeRequest;
import br.edu.iff.ccc._academics.service.AtividadeService;
import br.edu.iff.ccc._academics.service.PalestranteService;

@Controller
@RequestMapping("/eventos/{eventoId}/atividades")
public class AtividadeViewController {

    private final AtividadeService service;
    private final PalestranteService palestranteService;

    public AtividadeViewController(AtividadeService service, PalestranteService palestranteService) {
        this.service = service;
        this.palestranteService = palestranteService;
    }

    @GetMapping
    public String listar(@PathVariable UUID eventoId, Model model) {
        model.addAttribute("titulo", "Atividades do Evento");
        model.addAttribute("eventoId", eventoId);
        model.addAttribute("atividades", service.listarPorEvento(eventoId));
        return "atividades/lista";
    }

    @GetMapping("/nova")
    public String nova(@PathVariable UUID eventoId, Model model) {
        model.addAttribute("titulo", "Nova Atividade");
        model.addAttribute("eventoId", eventoId);
        model.addAttribute("palestrantes", palestranteService.listarTodos());
        return "atividades/form";
    }

    @PostMapping
    public String criar(@PathVariable UUID eventoId, AtividadeRequest request) {
        service.criar(eventoId, request);
        return "redirect:/eventos/" + eventoId + "/atividades";
    }

    @GetMapping("/{atividadeId}")
    public String detalhe(@PathVariable UUID eventoId, @PathVariable UUID atividadeId, Model model) {
        model.addAttribute("titulo", "Detalhes da Atividade");
        model.addAttribute("eventoId", eventoId);
        model.addAttribute("atividadeId", atividadeId);
        model.addAttribute("atividade", service.buscarPorId(atividadeId));
        return "atividades/detalhe";
    }

    @GetMapping("/{atividadeId}/editar")
    public String editar(@PathVariable UUID eventoId, @PathVariable UUID atividadeId, Model model) {
        model.addAttribute("titulo", "Editar Atividade");
        model.addAttribute("eventoId", eventoId);
        model.addAttribute("atividadeId", atividadeId);
        model.addAttribute("atividade", service.buscarPorId(atividadeId));
        model.addAttribute("palestrantes", palestranteService.listarTodos());
        return "atividades/form";
    }

    @PostMapping("/{atividadeId}")
    public String atualizar(@PathVariable UUID eventoId, @PathVariable UUID atividadeId, AtividadeRequest request) {
        service.atualizar(atividadeId, request);
        return "redirect:/eventos/" + eventoId + "/atividades/" + atividadeId;
    }

    @PostMapping("/{atividadeId}/excluir")
    public String excluir(@PathVariable UUID eventoId, @PathVariable UUID atividadeId) {
        service.remover(atividadeId);
        return "redirect:/eventos/" + eventoId + "/atividades";
    }

}
