package br.edu.iff.ccc._academics.controller.view;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc._academics.dto.OrganizadorRequest;
import br.edu.iff.ccc._academics.entities.Organizador;
import br.edu.iff.ccc._academics.service.OrganizadorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/organizadores")
public class OrganizadorViewController {

    private final OrganizadorService service;

    public OrganizadorViewController(OrganizadorService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Organizadores");
        model.addAttribute("organizadores", service.listarTodos());
        return "organizadores/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("titulo", "Novo Organizador");
        model.addAttribute("organizadorRequest", new OrganizadorRequest());
        return "organizadores/form";
    }

    @PostMapping
    public String criar(@Valid OrganizadorRequest organizadorRequest, BindingResult result, Model model) {
        if (organizadorRequest.getSenha() == null || organizadorRequest.getSenha().isBlank()) {
            result.rejectValue("senha", "senha.obrigatoria", "A senha é obrigatória no cadastro.");
        }
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Novo Organizador");
            return "organizadores/form";
        }
        service.criar(organizadorRequest);
        return "redirect:/organizadores";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable UUID id, Model model) {
        model.addAttribute("titulo", "Editar Organizador");
        model.addAttribute("id", id);
        model.addAttribute("organizadorRequest", paraRequest(service.buscarPorId(id)));
        return "organizadores/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable UUID id, @Valid OrganizadorRequest organizadorRequest,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Editar Organizador");
            model.addAttribute("id", id);
            return "organizadores/form";
        }
        service.atualizar(id, organizadorRequest);
        return "redirect:/organizadores";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable UUID id) {
        service.remover(id);
        return "redirect:/organizadores";
    }

    private OrganizadorRequest paraRequest(Organizador organizador) {
        OrganizadorRequest request = new OrganizadorRequest();
        request.setNome(organizador.getNome());
        request.setEmail(organizador.getEmail());
        return request;
    }

}
