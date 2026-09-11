package br.edu.iff.ccc._academics.controller.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.edu.iff.ccc._academics.exception.EntidadeDuplicadaException;
import br.edu.iff.ccc._academics.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc._academics.exception.RegraNegocioException;
import jakarta.servlet.http.HttpSession;

/**
 * Interceptação global de erros da aplicação. Cada exceção do domínio é
 * traduzida para uma página de erro amigável, renderizada com Thymeleaf.
 */
@ControllerAdvice(basePackages = "br.edu.iff.ccc._academics.controller.view")
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String tratarRecursoNaoEncontrado(RecursoNaoEncontradoException ex, Model model, HttpSession session) {
        return montarPagina(model, session, "Recurso não encontrado", ex.getMessage(), "error/404");
    }

    @ExceptionHandler(EntidadeDuplicadaException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String tratarEntidadeDuplicada(EntidadeDuplicadaException ex, Model model, HttpSession session) {
        return montarPagina(model, session, "Cadastro duplicado", ex.getMessage(), "error/409");
    }

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String tratarRegraNegocio(RegraNegocioException ex, Model model, HttpSession session) {
        return montarPagina(model, session, "Operação não permitida", ex.getMessage(), "error/400");
    }

    /**
     * Rede de proteção para violações que escapem das checagens de negócio e
     * cheguem até as restrições da própria tabela.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String tratarViolacaoDeIntegridade(DataIntegrityViolationException ex, Model model, HttpSession session) {
        log.warn("Violação de integridade no banco de dados", ex);
        return montarPagina(model, session, "Cadastro duplicado",
                "Os dados enviados violam uma restrição da tabela: campo obrigatório, valor duplicado"
                        + " ou registro ainda vinculado a outro cadastro.",
                "error/409");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String tratarErroInesperado(Exception ex, Model model, HttpSession session) {
        log.error("Erro inesperado ao processar a requisição", ex);
        return montarPagina(model, session, "Erro interno",
                "Não foi possível concluir a operação.", "error/500");
    }

    /**
     * Os métodos @ModelAttribute do SessaoModelAdvice não são aplicados aos
     * handlers de exceção, então os dados da sessão são repostos aqui para que
     * o cabeçalho continue mostrando o usuário logado nas páginas de erro.
     */
    private String montarPagina(Model model, HttpSession session, String titulo, String mensagem, String template) {
        model.addAttribute("titulo", titulo);
        model.addAttribute("mensagem", mensagem);
        model.addAttribute("usuarioLogado", session.getAttribute("usuarioLogado"));
        model.addAttribute("tipoUsuario", session.getAttribute("tipoUsuario"));
        return template;
    }

}
