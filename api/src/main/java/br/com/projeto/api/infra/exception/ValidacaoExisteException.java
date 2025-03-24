package br.com.projeto.api.infra.exception;

public class ValidacaoExisteException extends RuntimeException {
    public ValidacaoExisteException(String message) {
        super(message);
    }
}
