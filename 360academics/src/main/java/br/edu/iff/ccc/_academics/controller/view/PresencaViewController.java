package br.edu.iff.ccc._academics.controller.view;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/eventos/{eventoId}/atividades/{atividadeId}/presencas")
public class PresencaViewController {

    @GetMapping
    public String listar(@PathVariable Long eventoId, @PathVariable Long atividadeId, Model model) {
        model.addAttribute("titulo", "Controle de Presença");
        model.addAttribute("eventoId", eventoId);
        model.addAttribute("atividadeId", atividadeId);
        model.addAttribute("presencas", List.of());
        return "presencas/lista";
    }

    @PostMapping("/{inscricaoId}/confirmar")
    public String confirmar(@PathVariable Long eventoId, @PathVariable Long atividadeId,
            @PathVariable Long inscricaoId) {
        return "redirect:/eventos/" + eventoId + "/atividades/" + atividadeId + "/presencas";
    }

}
