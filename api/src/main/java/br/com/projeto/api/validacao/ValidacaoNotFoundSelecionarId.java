package br.com.projeto.api.validacao;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.modelo.interacoes.favoritar.DTOFavoritarSelecionarId;
import br.com.projeto.api.modelo.interacoes.favoritar.FilmeFavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoNotFoundSelecionarId implements ValidacaoFavoritar<Long>{

    @Autowired
    private FilmeFavoritoRepository filmeFavoritoRepository;

    @Override
    public void validar(Long idFilmeFavorito) {
        var filmeFavorito = filmeFavoritoRepository.findById(idFilmeFavorito);
        if(filmeFavorito.isEmpty()){
            throw new NotFoundException("Não foi possível encontrar o filme que você queria");
        }
    }
}
