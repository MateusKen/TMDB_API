package br.com.projeto.api.validacao.filme;

public interface ValidacaoFilme<T> {
    void validar(T dto);
    Class<T> getTipo();
}
