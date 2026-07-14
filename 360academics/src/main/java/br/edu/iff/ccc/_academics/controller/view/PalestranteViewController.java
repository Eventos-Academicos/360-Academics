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
@RequestMapping("/palestrantes")
public class PalestranteViewController {

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Palestrantes");
        model.addAttribute("palestrantes", List.of());
        return "palestrantes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("titulo", "Novo Palestrante");
        return "palestrantes/form";
    }

    @PostMapping
    public String criar(@RequestParam String nome,
            @RequestParam String email,
            @RequestParam String especialidade) {
        return "redirect:/palestrantes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar Palestrante");
        model.addAttribute("id", id);
        return "palestrantes/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String especialidade) {
        return "redirect:/palestrantes";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        return "redirect:/palestrantes";
    }

}
