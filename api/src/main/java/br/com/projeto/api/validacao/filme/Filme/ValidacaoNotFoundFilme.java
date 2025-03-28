package br.com.projeto.api.validacao.filme.Filme;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.modelo.filme.Filme;
import br.com.projeto.api.modelo.filme.FilmeRepository;
import br.com.projeto.api.validacao.filme.ValidacaoFilme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoNotFoundFilme implements ValidacaoFilme<Filme> {

    @Autowired
    private FilmeRepository filmeRepository;

    @Override
    public void validar(Filme dto) {
        var filme = filmeRepository.findById(dto.getId());
        if(filme.isEmpty()){
            throw new NotFoundException("Filme não encontrado!");
        }
    }

    @Override
    public Class<Filme> getTipo() {
        return Filme.class;
    }


}
