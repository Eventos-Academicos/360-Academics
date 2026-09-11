package br.edu.iff.ccc._academics.controller.view;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc._academics.dto.PalestranteRequest;
import br.edu.iff.ccc._academics.entities.Palestrante;
import br.edu.iff.ccc._academics.service.PalestranteService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/palestrantes")
public class PalestranteViewController {

    private final PalestranteService service;

    public PalestranteViewController(PalestranteService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Palestrantes");
        model.addAttribute("palestrantes", service.listarTodos());
        return "palestrantes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("titulo", "Novo Palestrante");
        model.addAttribute("palestranteRequest", new PalestranteRequest());
        return "palestrantes/form";
    }

    @PostMapping
    public String criar(@Valid PalestranteRequest palestranteRequest, BindingResult result, Model model) {
        if (palestranteRequest.getSenha() == null || palestranteRequest.getSenha().isBlank()) {
            result.rejectValue("senha", "senha.obrigatoria", "A senha é obrigatória no cadastro.");
        }
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Novo Palestrante");
            return "palestrantes/form";
        }
        service.criar(palestranteRequest);
        return "redirect:/palestrantes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable UUID id, Model model) {
        model.addAttribute("titulo", "Editar Palestrante");
        model.addAttribute("id", id);
        model.addAttribute("palestranteRequest", paraRequest(service.buscarPorId(id)));
        return "palestrantes/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable UUID id, @Valid PalestranteRequest palestranteRequest,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Editar Palestrante");
            model.addAttribute("id", id);
            return "palestrantes/form";
        }
        service.atualizar(id, palestranteRequest);
        return "redirect:/palestrantes";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable UUID id) {
        service.remover(id);
        return "redirect:/palestrantes";
    }

    private PalestranteRequest paraRequest(Palestrante palestrante) {
        PalestranteRequest request = new PalestranteRequest();
        request.setNome(palestrante.getNome());
        request.setEmail(palestrante.getEmail());
        request.setEspecialidade(palestrante.getEspecialidade());
        return request;
    }

}
