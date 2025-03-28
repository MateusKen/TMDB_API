package br.com.projeto.api.validacao.filme.Long;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.modelo.filme.FilmeRepository;
import br.com.projeto.api.validacao.filme.ValidacaoFilme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoNotFoundById implements ValidacaoFilme<Long> {

    @Autowired
    private FilmeRepository filmeRepository;

    @Override
    public void validar(Long id) {
        var filme = filmeRepository.findById(id);
        if(filme.isEmpty()){
            throw new NotFoundException("Filme não encontrado");
        }
    }

    @Override
    public Class<Long> getTipo() {
        return Long.class;
    }
}
