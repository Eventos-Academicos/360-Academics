package br.edu.iff.ccc._academics.controller.view;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc._academics.dto.CertificadoRequest;
import br.edu.iff.ccc._academics.entities.Certificado;
import br.edu.iff.ccc._academics.entities.Palestrante;
import br.edu.iff.ccc._academics.exception.RegraNegocioException;
import br.edu.iff.ccc._academics.service.CertificadoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/certificados")
public class CertificadoViewController {

    private final CertificadoService service;

    public CertificadoViewController(CertificadoService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Meus Certificados");
        model.addAttribute("certificados", service.listarTodos());
        return "certificados/lista";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable UUID id, Model model) {
        model.addAttribute("titulo", "Certificado");
        model.addAttribute("id", id);
        model.addAttribute("certificado", service.buscarPorId(id));
        return "certificados/detalhe";
    }

    @PostMapping("/gerar/{inscricaoId}/{atividadeId}")
    public String gerar(@PathVariable UUID inscricaoId, @PathVariable UUID atividadeId, HttpSession session) {
        Palestrante palestrante = exigirPalestranteLogado(session);
        service.gerar(inscricaoId, atividadeId, palestrante.getId());
        return "redirect:/certificados";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable UUID id, Model model) {
        model.addAttribute("titulo", "Editar Certificado");
        model.addAttribute("id", id);
        model.addAttribute("certificadoRequest", paraRequest(service.buscarPorId(id)));
        return "certificados/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable UUID id, @Valid CertificadoRequest certificadoRequest,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Editar Certificado");
            model.addAttribute("id", id);
            return "certificados/form";
        }
        service.atualizar(id, certificadoRequest);
        return "redirect:/certificados/" + id;
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable UUID id) {
        service.remover(id);
        return "redirect:/certificados";
    }

    private CertificadoRequest paraRequest(Certificado certificado) {
        CertificadoRequest request = new CertificadoRequest();
        request.setCodigoValidacao(certificado.getCodigoValidacao());
        return request;
    }

    private Palestrante exigirPalestranteLogado(HttpSession session) {
        Object usuario = session.getAttribute("usuarioLogado");
        if (!(usuario instanceof Palestrante palestrante)) {
            throw new RegraNegocioException("Você precisa entrar como palestrante para emitir certificados.");
        }
        return palestrante;
    }

}
