package br.edu.iff.ccc._academics.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sobre")
public class SobreController {

    @GetMapping
    public String mostrarSobre(Model model) {
        model.addAttribute("titulo", "Sobre");
        model.addAttribute("texto",
            "Essa aplicação é simples e serve para organizar eventos e atividades acadêmicas.");
        return "sobre";
    }
}