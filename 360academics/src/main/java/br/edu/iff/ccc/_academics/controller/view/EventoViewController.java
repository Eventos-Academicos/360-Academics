package br.edu.iff.ccc._academics.controller.view;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc._academics.dto.EventoRequest;
import br.edu.iff.ccc._academics.entities.Evento;
import br.edu.iff.ccc._academics.exception.RegraNegocioException;
import br.edu.iff.ccc._academics.service.EventoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

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
        model.addAttribute("eventoRequest", new EventoRequest());
        return "eventos/form";
    }

    @PostMapping
    public String criar(@Valid EventoRequest eventoRequest, BindingResult result, Model model, HttpSession session) {
        exigirOrganizadorLogado(session);
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Novo Evento");
            return "eventos/form";
        }
        service.criar(eventoRequest);
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
        model.addAttribute("eventoRequest", paraRequest(service.buscarPorId(id)));
        return "eventos/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable UUID id, @Valid EventoRequest eventoRequest, BindingResult result,
            Model model, HttpSession session) {
        exigirOrganizadorLogado(session);
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Editar Evento");
            model.addAttribute("id", id);
            return "eventos/form";
        }
        service.atualizar(id, eventoRequest);
        return "redirect:/eventos/" + id;
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable UUID id, HttpSession session) {
        exigirOrganizadorLogado(session);
        service.remover(id);
        return "redirect:/eventos";
    }

    private EventoRequest paraRequest(Evento evento) {
        EventoRequest request = new EventoRequest();
        request.setNome(evento.getNome());
        request.setDescricao(evento.getDescricao());
        request.setLocal(evento.getLocal());
        request.setDataInicio(evento.getDataInicio());
        request.setDataFim(evento.getDataFim());
        request.setLimiteVagas(evento.getLimiteVagas());
        return request;
    }

    private void exigirOrganizadorLogado(HttpSession session) {
        if (!"ORGANIZADOR".equals(session.getAttribute("tipoUsuario"))) {
            throw new RegraNegocioException("Você precisa entrar como organizador para gerenciar eventos.");
        }
    }

}
