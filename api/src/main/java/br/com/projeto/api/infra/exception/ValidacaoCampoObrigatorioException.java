package br.com.projeto.api.infra.exception;

public class ValidacaoCampoObrigatorioException extends RuntimeException {
    public ValidacaoCampoObrigatorioException(String message) {
        super(message);
    }
}
