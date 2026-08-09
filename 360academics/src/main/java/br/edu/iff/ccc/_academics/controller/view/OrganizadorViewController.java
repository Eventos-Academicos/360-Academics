package br.edu.iff.ccc._academics.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc._academics.dto.OrganizadorRequest;
import br.edu.iff.ccc._academics.service.OrganizadorService;

@Controller
@RequestMapping("/organizadores")
public class OrganizadorViewController {

    private final OrganizadorService service;

    public OrganizadorViewController(OrganizadorService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Organizadores");
        model.addAttribute("organizadores", service.listarTodos());
        return "organizadores/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("titulo", "Novo Organizador");
        return "organizadores/form";
    }

    @PostMapping
    public String criar(OrganizadorRequest request) {
        service.criar(request);
        return "redirect:/organizadores";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar Organizador");
        model.addAttribute("id", id);
        model.addAttribute("organizador", service.buscarPorId(id));
        return "organizadores/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, OrganizadorRequest request) {
        service.atualizar(id, request);
        return "redirect:/organizadores";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.remover(id);
        return "redirect:/organizadores";
    }

}
