package br.edu.iff.ccc._academics.controller.view;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class SessaoModelAdvice {

    @ModelAttribute
    public void adicionarUsuarioLogado(HttpSession session, Model model) {
        model.addAttribute("usuarioLogado", session.getAttribute("usuarioLogado"));
        model.addAttribute("tipoUsuario", session.getAttribute("tipoUsuario"));
    }

}
