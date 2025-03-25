package br.com.projeto.api.validacao;

import br.com.projeto.api.infra.exception.ValidacaoExisteException;
import br.com.projeto.api.modelo.interacoes.favoritar.DTOFavoritar;
import br.com.projeto.api.modelo.interacoes.favoritar.FilmeFavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoFavoritarExists implements ValidacaoFavoritar<DTOFavoritar>{

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
