package br.edu.iff.ccc._academics.controller.view;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc._academics.dto.EventoRequest;
import br.edu.iff.ccc._academics.exception.RegraNegocioException;
import br.edu.iff.ccc._academics.service.EventoService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/eventos")
public class EventoViewController {

    private final EventoService service;

    public EventoViewController(EventoService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Eventos");
        model.addAttribute("eventos", service.listarTodos());
        return "eventos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("titulo", "Novo Evento");
        return "eventos/form";
    }

    @PostMapping
    public String criar(EventoRequest request, HttpSession session) {
        exigirOrganizadorLogado(session);
        service.criar(request);
        return "redirect:/eventos";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable UUID id, Model model) {
        model.addAttribute("titulo", "Detalhes do Evento");
        model.addAttribute("id", id);
        model.addAttribute("evento", service.buscarPorId(id));
        return "eventos/detalhe";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable UUID id, Model model) {
        model.addAttribute("titulo", "Editar Evento");
        model.addAttribute("id", id);
        model.addAttribute("evento", service.buscarPorId(id));
        return "eventos/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable UUID id, EventoRequest request, HttpSession session) {
        exigirOrganizadorLogado(session);
        service.atualizar(id, request);
        return "redirect:/eventos/" + id;
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable UUID id) {
        service.remover(id);
        return "redirect:/eventos";
    }

    private void exigirOrganizadorLogado(HttpSession session) {
        if (!"ORGANIZADOR".equals(session.getAttribute("tipoUsuario"))) {
            throw new RegraNegocioException("Você precisa entrar como organizador para gerenciar eventos.");
        }
    }

}
