package br.edu.iff.ccc._academics.controller.view;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/historico")
public class HistoricoViewController {

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Histórico de Participação");
        model.addAttribute("historico", List.of());
        return "historico/lista";
    }

}
