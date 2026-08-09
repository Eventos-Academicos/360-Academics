package br.edu.iff.ccc._academics.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc._academics.dto.PalestranteRequest;
import br.edu.iff.ccc._academics.service.PalestranteService;

@Controller
@RequestMapping("/palestrantes")
public class PalestranteViewController {

    private final PalestranteService service;

    public PalestranteViewController(PalestranteService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Palestrantes");
        model.addAttribute("palestrantes", service.listarTodos());
        return "palestrantes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("titulo", "Novo Palestrante");
        return "palestrantes/form";
    }

    @PostMapping
    public String criar(PalestranteRequest request) {
        service.criar(request);
        return "redirect:/palestrantes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar Palestrante");
        model.addAttribute("id", id);
        model.addAttribute("palestrante", service.buscarPorId(id));
        return "palestrantes/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, PalestranteRequest request) {
        service.atualizar(id, request);
        return "redirect:/palestrantes";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.remover(id);
        return "redirect:/palestrantes";
    }

}
