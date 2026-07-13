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
@RequestMapping("/participantes")
public class ParticipanteViewController {

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Participantes");
        model.addAttribute("participantes", List.of());
        return "participantes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("titulo", "Novo Participante");
        return "participantes/form";
    }

    @PostMapping
    public String criar(@RequestParam String nome,
            @RequestParam String email,
            @RequestParam String matricula) {
        return "redirect:/participantes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar Participante");
        model.addAttribute("id", id);
        return "participantes/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String matricula) {
        return "redirect:/participantes";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        return "redirect:/participantes";
    }

}
