package br.com.projeto.api.validacao;

public interface ValidacaoFavoritar<T> {
    void validar(T dto);

    Class<T> getTipo();
}
