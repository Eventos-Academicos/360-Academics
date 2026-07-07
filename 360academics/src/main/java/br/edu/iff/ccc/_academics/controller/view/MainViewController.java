package br.edu.iff.ccc._academics.controller.view;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inicial")
public class MainViewController {

    @GetMapping
    public String getPaginaInicial(Model model) {
        model.addAttribute("titulo", "Início");
        model.addAttribute("descricao",
                "Plataforma acadêmica para organização de eventos, atividades e participação de estudantes.");
        model.addAttribute("funcionalidades", List.of(
                "Cadastro de eventos acadêmicos",
                "Gerenciamento de participantes",
                "Controle de presença e certificados"));
        return "inicial";
    }

}