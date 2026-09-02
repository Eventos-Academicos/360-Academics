package br.edu.iff.ccc._academics.controller.view;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.iff.ccc._academics.exception.RegraNegocioException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RegraNegocioExceptionHandler {

    @ExceptionHandler(RegraNegocioException.class)
    public String tratar(RegraNegocioException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        return redirecionarComErro(ex.getMessage(), request, redirectAttributes);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String tratarViolacaoDeIntegridade(DataIntegrityViolationException ex, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        return redirecionarComErro(
                "Não foi possível gravar no banco: os dados violam uma restrição da tabela"
                        + " (campo obrigatório, valor duplicado ou registro ainda vinculado a outro cadastro).",
                request, redirectAttributes);
    }

    private String redirecionarComErro(String mensagem, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("erro", mensagem);
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/inicial");
    }

}
