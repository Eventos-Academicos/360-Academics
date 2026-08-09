package br.edu.iff.ccc._academics.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc._academics.dto.ParticipanteRequest;
import br.edu.iff.ccc._academics.service.ParticipanteService;

@Controller
@RequestMapping("/participantes")
public class ParticipanteViewController {

    private final ParticipanteService service;

    public ParticipanteViewController(ParticipanteService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Participantes");
        model.addAttribute("participantes", service.listarTodos());
        return "participantes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("titulo", "Novo Participante");
        return "participantes/form";
    }

    @PostMapping
    public String criar(ParticipanteRequest request) {
        service.criar(request);
        return "redirect:/participantes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar Participante");
        model.addAttribute("id", id);
        model.addAttribute("participante", service.buscarPorId(id));
        return "participantes/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, ParticipanteRequest request) {
        service.atualizar(id, request);
        return "redirect:/participantes";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.remover(id);
        return "redirect:/participantes";
    }

}
