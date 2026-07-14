package br.edu.iff.ccc._academics.controller.view;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/organizadores")
public class OrganizadorViewController {

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Organizadores");
        model.addAttribute("organizadores", List.of());
        return "organizadores/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("titulo", "Novo Organizador");
        return "organizadores/form";
    }

    @PostMapping
    public String criar(@RequestParam String nome, @RequestParam String email) {
        return "redirect:/organizadores";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar Organizador");
        model.addAttribute("id", id);
        return "organizadores/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @RequestParam String nome, @RequestParam String email) {
        return "redirect:/organizadores";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        return "redirect:/organizadores";
    }

}
