package br.edu.iff.ccc._academics.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.iff.ccc._academics.dto.InscricaoRequest;
import br.edu.iff.ccc._academics.entities.Participante;
import br.edu.iff.ccc._academics.exception.RegraNegocioException;
import br.edu.iff.ccc._academics.service.InscricaoService;
import jakarta.servlet.http.HttpSession;

@Controller
public class InscricaoViewController {

    private final InscricaoService service;

    public InscricaoViewController(InscricaoService service) {
        this.service = service;
    }

    @GetMapping("/inscricoes")
    public String listar(Model model) {
        model.addAttribute("titulo", "Minhas Inscrições");
        model.addAttribute("inscricoes", service.listarTodos());
        return "inscricoes/lista";
    }

    @PostMapping("/inscricoes/{id}/cancelar")
    public String cancelar(@PathVariable Long id) {
        service.atualizarStatus(id, "Cancelada");
        return "redirect:/inscricoes";
    }

    @PostMapping("/inscricoes/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.remover(id);
        return "redirect:/inscricoes";
    }

    @GetMapping("/eventos/{eventoId}/inscricoes/nova")
    public String nova(@PathVariable Long eventoId, Model model) {
        model.addAttribute("titulo", "Confirmar Inscrição");
        model.addAttribute("eventoId", eventoId);
        return "inscricoes/form";
    }

    @PostMapping("/eventos/{eventoId}/inscricoes")
    public String criar(@PathVariable Long eventoId, HttpSession session) {
        Participante participante = exigirParticipanteLogado(session);
        InscricaoRequest request = new InscricaoRequest();
        request.setParticipanteId(participante.getId());
        service.criar(eventoId, request);
        return "redirect:/inscricoes";
    }

    private Participante exigirParticipanteLogado(HttpSession session) {
        Object usuario = session.getAttribute("usuarioLogado");
        if (!(usuario instanceof Participante participante)) {
            throw new RegraNegocioException("Você precisa entrar como participante para se inscrever em um evento.");
        }
        return participante;
    }

}
