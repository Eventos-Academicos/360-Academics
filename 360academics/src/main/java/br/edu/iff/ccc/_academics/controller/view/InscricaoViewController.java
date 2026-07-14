package br.edu.iff.ccc._academics.controller.view;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class InscricaoViewController {

    @GetMapping("/inscricoes")
    public String minhasInscricoes(Model model) {
        model.addAttribute("titulo", "Minhas Inscrições");
        model.addAttribute("inscricoes", List.of());
        return "inscricoes/lista";
    }

    @PostMapping("/inscricoes/{id}/cancelar")
    public String cancelar(@PathVariable Long id) {
        return "redirect:/inscricoes";
    }

    @GetMapping("/eventos/{eventoId}/inscricoes/nova")
    public String nova(@PathVariable Long eventoId, Model model) {
        model.addAttribute("titulo", "Confirmar Inscrição");
        model.addAttribute("eventoId", eventoId);
        return "inscricoes/form";
    }

    @PostMapping("/eventos/{eventoId}/inscricoes")
    public String criar(@PathVariable Long eventoId) {
        return "redirect:/inscricoes";
    }

}
