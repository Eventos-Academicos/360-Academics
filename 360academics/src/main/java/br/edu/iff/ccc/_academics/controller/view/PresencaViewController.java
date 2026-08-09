package br.edu.iff.ccc._academics.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc._academics.service.PresencaService;

@Controller
@RequestMapping("/eventos/{eventoId}/atividades/{atividadeId}/presencas")
public class PresencaViewController {

    private final PresencaService service;

    public PresencaViewController(PresencaService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(@PathVariable Long eventoId, @PathVariable Long atividadeId, Model model) {
        model.addAttribute("titulo", "Controle de Presença");
        model.addAttribute("eventoId", eventoId);
        model.addAttribute("atividadeId", atividadeId);
        model.addAttribute("presencas", service.listarPorAtividade(atividadeId));
        return "presencas/lista";
    }

    @PostMapping("/{inscricaoId}/confirmar")
    public String confirmar(@PathVariable Long eventoId, @PathVariable Long atividadeId,
            @PathVariable Long inscricaoId) {
        service.confirmar(atividadeId, inscricaoId);
        return "redirect:/eventos/" + eventoId + "/atividades/" + atividadeId + "/presencas";
    }

    @PostMapping("/{inscricaoId}/desconfirmar")
    public String desconfirmar(@PathVariable Long eventoId, @PathVariable Long atividadeId,
            @PathVariable Long inscricaoId) {
        service.desconfirmar(atividadeId, inscricaoId);
        return "redirect:/eventos/" + eventoId + "/atividades/" + atividadeId + "/presencas";
    }

}
