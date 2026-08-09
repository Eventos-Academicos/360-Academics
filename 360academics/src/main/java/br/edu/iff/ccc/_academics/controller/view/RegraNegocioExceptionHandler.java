package br.edu.iff.ccc._academics.controller.view;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.iff.ccc._academics.exception.RegraNegocioException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RegraNegocioExceptionHandler {

    @ExceptionHandler(RegraNegocioException.class)
    public String tratar(RegraNegocioException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("erro", ex.getMessage());
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/inicial");
    }

}
