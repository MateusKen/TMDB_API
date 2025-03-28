package br.com.projeto.api.validacao.filme.Filme;

import br.com.projeto.api.infra.exception.ValidacaoExisteException;
import br.com.projeto.api.modelo.filme.Filme;
import br.com.projeto.api.modelo.filme.FilmeRepository;
import br.com.projeto.api.validacao.filme.ValidacaoFilme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoExistsFilme implements ValidacaoFilme<Filme> {

    @Autowired
    private FilmeRepository filmeRepository;

    @Override
    public void validar(Filme dto) {
        var filmeBusca = filmeRepository.findById(dto.getId());
        if(filmeBusca.isPresent()){
            throw new ValidacaoExisteException("Filme já cadastrado!");
        }
    }

    @Override
    public Class<Filme> getTipo() {
        return Filme.class;
    }
}
