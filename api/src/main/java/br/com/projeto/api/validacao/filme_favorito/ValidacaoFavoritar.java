package br.com.projeto.api.validacao.filme_favorito;

public interface ValidacaoFavoritar<T> {
    void validar(T dto);
    Class<T> getTipo();
}
