package br.com.projeto.api.validacao.filme_favorito.Long;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.modelo.interacoes.favoritar.FilmeFavoritoRepository;
import br.com.projeto.api.validacao.filme.ValidacaoFilme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarNotFoundFilmeFavorito implements ValidacaoFilme<Long> {

    @Autowired
    private FilmeFavoritoRepository filmeFavoritoRepository;

    @Override
    public void validar(Long ifFilmeFavorito) {
        var filme = filmeFavoritoRepository.findById(ifFilmeFavorito);
        if (filme.isEmpty()) {
            throw new NotFoundException("Filme favorito não encontrado!");
        }
    }

    @Override
    public Class<Long> getTipo(){
        return Long.class;
    }
}
