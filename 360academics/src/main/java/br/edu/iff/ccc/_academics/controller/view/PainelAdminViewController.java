package br.edu.iff.ccc._academics.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class PainelAdminViewController {

    @GetMapping
    public String painel(Model model) {
        model.addAttribute("titulo", "Painel Administrativo");
        return "admin/painel";
    }

}
