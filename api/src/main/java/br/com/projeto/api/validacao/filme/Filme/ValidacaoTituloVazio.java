package br.com.projeto.api.validacao.filme.Filme;

import br.com.projeto.api.infra.exception.ValidacaoCampoObrigatorioException;
import br.com.projeto.api.modelo.filme.Filme;
import br.com.projeto.api.modelo.filme.FilmeRepository;
import br.com.projeto.api.validacao.filme.ValidacaoFilme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoTituloVazio implements ValidacaoFilme<Filme> {

    @Autowired
    private FilmeRepository filmeRepository;

    @Override
    public void validar(Filme dto) {
        var titulo = dto.getTitle();
        if(titulo.isEmpty()){
            throw new ValidacaoCampoObrigatorioException("Título não pode ser vazio!");
        }
    }

    @Override
    public Class<Filme> getTipo() {
        return Filme.class;
    }
}
