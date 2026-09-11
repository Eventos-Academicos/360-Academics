package br.edu.iff.ccc._academics.exception;

/**
 * Lançada quando o cadastro violaria uma restrição de unicidade do domínio,
 * como um evento com nome já existente ou um usuário com e-mail já cadastrado.
 */
public class EntidadeDuplicadaException extends RuntimeException {

    public EntidadeDuplicadaException(String message) {
        super(message);
    }

}
