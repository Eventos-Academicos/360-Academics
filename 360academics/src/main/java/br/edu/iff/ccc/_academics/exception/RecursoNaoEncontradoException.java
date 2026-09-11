package br.edu.iff.ccc._academics.exception;

/**
 * Lançada quando uma busca no repositório não encontra o registro solicitado.
 */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String message) {
        super(message);
    }

}
