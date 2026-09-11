package br.edu.iff.ccc._academics.exception;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Tratamento global de erros da API REST, com respostas no padrão RFC 9457 (Problem Details).
 * Fica restrito aos controllers REST para não interferir nas páginas de erro das telas.
 */
@RestControllerAdvice(basePackages = "br.edu.iff.ccc._academics.controller.rest")
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ProblemDetail handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex, WebRequest request) {
        return criarProblema(HttpStatus.NOT_FOUND, "Recurso Não Encontrado", ex.getMessage(),
                "recurso-nao-encontrado", request);
    }

    @ExceptionHandler(EntidadeDuplicadaException.class)
    public ProblemDetail handleEntidadeDuplicada(EntidadeDuplicadaException ex, WebRequest request) {
        return criarProblema(HttpStatus.CONFLICT, "Entidade Duplicada", ex.getMessage(),
                "entidade-duplicada", request);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ProblemDetail handleRegraNegocio(RegraNegocioException ex, WebRequest request) {
        return criarProblema(HttpStatus.UNPROCESSABLE_CONTENT, "Regra de Negócio Violada", ex.getMessage(),
                "regra-de-negocio", request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleViolacaoDeIntegridade(DataIntegrityViolationException ex, WebRequest request) {
        log.warn("Violação de integridade no banco de dados: {}", ex.getMostSpecificCause().getMessage());
        return criarProblema(HttpStatus.CONFLICT, "Violação de Integridade",
                "Os dados enviados violam uma restrição do banco de dados.", "violacao-de-integridade", request);
    }

    /**
     * Captura os erros do @Valid e devolve cada campo defeituoso dentro da
     * propriedade invalid_params, na estrutura da RFC 9457.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problemDetail = criarProblema(HttpStatus.BAD_REQUEST, "Dados Inválidos",
                "A requisição possui campos inválidos. Verifique os detalhes fornecidos.",
                "dados-invalidos", request);

        Map<String, String> errosDeCampo = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errosDeCampo.put(error.getField(), error.getDefaultMessage());
        }
        problemDetail.setProperty("invalid_params", errosDeCampo);

        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    private ProblemDetail criarProblema(HttpStatus status, String titulo, String detalhe, String tipo,
            WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detalhe);
        problemDetail.setType(URI.create("/api/v1/erros/" + tipo));
        problemDetail.setTitle(titulo);
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

}
