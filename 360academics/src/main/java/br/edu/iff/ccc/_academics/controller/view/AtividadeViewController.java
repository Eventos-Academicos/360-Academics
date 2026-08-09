package br.edu.iff.ccc._academics.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc._academics.dto.AtividadeRequest;
import br.edu.iff.ccc._academics.service.AtividadeService;

@Controller
@RequestMapping("/eventos/{eventoId}/atividades")
public class AtividadeViewController {

    private final AtividadeService service;

    public AtividadeViewController(AtividadeService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(@PathVariable Long eventoId, Model model) {
        model.addAttribute("titulo", "Atividades do Evento");
        model.addAttribute("eventoId", eventoId);
        model.addAttribute("atividades", service.listarPorEvento(eventoId));
        return "atividades/lista";
    }

    @GetMapping("/nova")
    public String nova(@PathVariable Long eventoId, Model model) {
        model.addAttribute("titulo", "Nova Atividade");
        model.addAttribute("eventoId", eventoId);
        return "atividades/form";
    }

    @PostMapping
    public String criar(@PathVariable Long eventoId, AtividadeRequest request) {
        service.criar(eventoId, request);
        return "redirect:/eventos/" + eventoId + "/atividades";
    }

    @GetMapping("/{atividadeId}")
    public String detalhe(@PathVariable Long eventoId, @PathVariable Long atividadeId, Model model) {
        model.addAttribute("titulo", "Detalhes da Atividade");
        model.addAttribute("eventoId", eventoId);
        model.addAttribute("atividadeId", atividadeId);
        model.addAttribute("atividade", service.buscarPorId(atividadeId));
        return "atividades/detalhe";
    }

    @GetMapping("/{atividadeId}/editar")
    public String editar(@PathVariable Long eventoId, @PathVariable Long atividadeId, Model model) {
        model.addAttribute("titulo", "Editar Atividade");
        model.addAttribute("eventoId", eventoId);
        model.addAttribute("atividadeId", atividadeId);
        model.addAttribute("atividade", service.buscarPorId(atividadeId));
        return "atividades/form";
    }

    @PostMapping("/{atividadeId}")
    public String atualizar(@PathVariable Long eventoId, @PathVariable Long atividadeId, AtividadeRequest request) {
        service.atualizar(atividadeId, request);
        return "redirect:/eventos/" + eventoId + "/atividades/" + atividadeId;
    }

    @PostMapping("/{atividadeId}/excluir")
    public String excluir(@PathVariable Long eventoId, @PathVariable Long atividadeId) {
        service.remover(atividadeId);
        return "redirect:/eventos/" + eventoId + "/atividades";
    }

}
