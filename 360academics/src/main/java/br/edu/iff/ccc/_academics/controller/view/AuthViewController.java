package br.edu.iff.ccc._academics.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthViewController {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("titulo", "Entrar");
        return "auth/login";
    }

    @PostMapping("/login")
    public String autenticar(@RequestParam String email, @RequestParam String senha) {
        return "redirect:/inicial";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/inicial";
    }

    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("titulo", "Criar Conta");
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String criarConta(@RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String matricula) {
        return "redirect:/login";
    }

}
