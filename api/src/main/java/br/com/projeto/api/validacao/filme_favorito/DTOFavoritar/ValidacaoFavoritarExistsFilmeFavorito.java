package br.com.projeto.api.validacao.filme_favorito.DTOFavoritar;

import br.com.projeto.api.infra.exception.ValidacaoExisteException;
import br.com.projeto.api.modelo.interacoes.favoritar.DTOFavoritar;
import br.com.projeto.api.modelo.interacoes.favoritar.FilmeFavoritoRepository;
import br.com.projeto.api.validacao.filme_favorito.ValidacaoFavoritar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoFavoritarExistsFilmeFavorito implements ValidacaoFavoritar<DTOFavoritar> {

    @Autowired
    private FilmeFavoritoRepository filmeFavoritoRepository;

    @Override
    public void validar(DTOFavoritar dto) {
        var filmeExists = filmeFavoritoRepository.existsByUsuarioIdAndFilmeId(dto.idUsuario(),dto.idFilme());
        if(filmeExists){
            throw new ValidacaoExisteException("Filme já favoritado!");
        }
    }

    @Override
    public Class<DTOFavoritar> getTipo(){
        return DTOFavoritar.class;
    }
}
