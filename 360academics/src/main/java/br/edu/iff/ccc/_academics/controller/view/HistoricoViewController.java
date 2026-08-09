package br.edu.iff.ccc._academics.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc._academics.dto.HistoricoRequest;
import br.edu.iff.ccc._academics.service.HistoricoService;

@Controller
@RequestMapping("/historico")
public class HistoricoViewController {

    private final HistoricoService service;

    public HistoricoViewController(HistoricoService service) {
        this.service = service;
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
        return "historico/form";
    }

    @PostMapping
    public String criar(HistoricoRequest request) {
        service.criar(request);
        return "redirect:/historico";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar Participação");
        model.addAttribute("id", id);
        model.addAttribute("item", service.buscarPorId(id));
        return "historico/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, HistoricoRequest request) {
        service.atualizar(id, request);
        return "redirect:/historico";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.remover(id);
        return "redirect:/historico";
    }

}
