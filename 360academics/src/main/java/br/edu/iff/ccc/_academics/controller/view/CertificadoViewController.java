package br.edu.iff.ccc._academics.controller.view;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/certificados")
public class CertificadoViewController {

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Meus Certificados");
        model.addAttribute("certificados", List.of());
        return "certificados/lista";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Certificado");
        model.addAttribute("id", id);
        return "certificados/detalhe";
    }

    @PostMapping("/gerar/{inscricaoId}")
    public String gerar(@PathVariable Long inscricaoId) {
        return "redirect:/certificados";
    }

}
