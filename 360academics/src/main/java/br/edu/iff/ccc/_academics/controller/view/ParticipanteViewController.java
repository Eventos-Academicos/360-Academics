package br.edu.iff.ccc._academics.controller.view;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.iff.ccc._academics.entities.Participante;
import br.edu.iff.ccc._academics.repository.ParticipanteRepositorio;

@Controller
@RequestMapping("/participantes")
public class ParticipanteViewController {

    private final ParticipanteRepositorio repositorio = new ParticipanteRepositorio();
    private Long proximoId = 1L;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Participantes");
        model.addAttribute("participantes", repositorio.listarTodos());
        return "participantes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("titulo", "Novo Participante");
        return "participantes/form";
    }

    @PostMapping
    public String criar(@RequestParam String nome,
            @RequestParam String email,
            @RequestParam String matricula) {
        Participante participante = new Participante();
        participante.setId(proximoId++);
        participante.setNome(nome);
        participante.setEmail(email);
        participante.setMatricula(matricula);

        repositorio.salvar(participante);
        return "redirect:/participantes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Participante participante = repositorio.buscarPorId(id);
        model.addAttribute("titulo", "Editar Participante");
        model.addAttribute("id", id);
        model.addAttribute("participante", participante);
        return "participantes/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String matricula) {
        Participante participante = repositorio.buscarPorId(id);
        if (participante != null) {
            participante.setNome(nome);
            participante.setEmail(email);
            participante.setMatricula(matricula);
        }
        return "redirect:/participantes";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        Participante participante = repositorio.buscarPorId(id);
        if (participante != null) {
            repositorio.remover(participante);
        }
        return "redirect:/participantes";
    }

}
