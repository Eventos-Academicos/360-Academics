package br.edu.iff.ccc._academics.controller.view;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc._academics.dto.ParticipanteRequest;
import br.edu.iff.ccc._academics.entities.Participante;
import br.edu.iff.ccc._academics.service.ParticipanteService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/participantes")
public class ParticipanteViewController {

    private final ParticipanteService service;

    public ParticipanteViewController(ParticipanteService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Participantes");
        model.addAttribute("participantes", service.listarTodos());
        return "participantes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("titulo", "Novo Participante");
        model.addAttribute("participanteRequest", new ParticipanteRequest());
        return "participantes/form";
    }

    @PostMapping
    public String criar(@Valid ParticipanteRequest participanteRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Novo Participante");
            return "participantes/form";
        }
        service.criar(participanteRequest);
        return "redirect:/participantes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable UUID id, Model model) {
        model.addAttribute("titulo", "Editar Participante");
        model.addAttribute("id", id);
        model.addAttribute("participanteRequest", paraRequest(service.buscarPorId(id)));
        return "participantes/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable UUID id, @Valid ParticipanteRequest participanteRequest,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Editar Participante");
            model.addAttribute("id", id);
            return "participantes/form";
        }
        service.atualizar(id, participanteRequest);
        return "redirect:/participantes";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable UUID id) {
        service.remover(id);
        return "redirect:/participantes";
    }

    private ParticipanteRequest paraRequest(Participante participante) {
        ParticipanteRequest request = new ParticipanteRequest();
        request.setNome(participante.getNome());
        request.setEmail(participante.getEmail());
        request.setMatricula(participante.getMatricula());
        return request;
    }

}
