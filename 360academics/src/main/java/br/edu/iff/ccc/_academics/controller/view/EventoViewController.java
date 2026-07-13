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
@RequestMapping("/eventos")
public class EventoViewController {

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Eventos");
        model.addAttribute("eventos", List.of());
        return "eventos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("titulo", "Novo Evento");
        return "eventos/form";
    }

    @PostMapping
    public String criar(@RequestParam String nome,
            @RequestParam String descricao,
            @RequestParam String dataInicio,
            @RequestParam String dataFim,
            @RequestParam int limiteVagas) {
        return "redirect:/eventos";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Detalhes do Evento");
        model.addAttribute("id", id);
        return "eventos/detalhe";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar Evento");
        model.addAttribute("id", id);
        return "eventos/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
            @RequestParam String nome,
            @RequestParam String descricao,
            @RequestParam String dataInicio,
            @RequestParam String dataFim,
            @RequestParam int limiteVagas) {
        return "redirect:/eventos/" + id;
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        return "redirect:/eventos";
    }

}
