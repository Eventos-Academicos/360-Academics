package br.edu.iff.ccc._academics.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.iff.ccc._academics.dto.ParticipanteRequest;
import br.edu.iff.ccc._academics.entities.Participante;
import br.edu.iff.ccc._academics.entities.Usuario;
import br.edu.iff.ccc._academics.service.AuthService;
import br.edu.iff.ccc._academics.service.ParticipanteService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthViewController {

    private final AuthService authService;
    private final ParticipanteService participanteService;

    public AuthViewController(AuthService authService, ParticipanteService participanteService) {
        this.authService = authService;
        this.participanteService = participanteService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("titulo", "Entrar");
        return "auth/login";
    }

    @PostMapping("/login")
    public String autenticar(@RequestParam String email, @RequestParam String senha, HttpSession session) {
        Usuario usuario = authService.autenticar(email, senha);
        entrar(session, usuario);
        return "redirect:/inicial";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/inicial";
    }

    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("titulo", "Criar Conta");
        model.addAttribute("participanteRequest", new ParticipanteRequest());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String criarConta(@Valid ParticipanteRequest participanteRequest, BindingResult result, Model model,
            HttpSession session) {
        if (participanteRequest.getSenha() == null || participanteRequest.getSenha().isBlank()) {
            result.rejectValue("senha", "senha.obrigatoria", "A senha é obrigatória.");
        }
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Criar Conta");
            return "auth/registro";
        }
        Participante participante = participanteService.criar(participanteRequest);
        entrar(session, participante);
        return "redirect:/inicial";
    }

    private void entrar(HttpSession session, Usuario usuario) {
        session.setAttribute("usuarioLogado", usuario);
        session.setAttribute("tipoUsuario", authService.tipoDe(usuario));
    }

}
