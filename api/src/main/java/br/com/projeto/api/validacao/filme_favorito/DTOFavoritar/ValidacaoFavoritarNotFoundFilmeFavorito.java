package br.com.projeto.api.validacao.filme_favorito.DTOFavoritar;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.modelo.filme.FilmeRepository;
import br.com.projeto.api.modelo.interacoes.favoritar.DTOFavoritar;
import br.com.projeto.api.modelo.usuario.UsuarioRepository;
import br.com.projeto.api.validacao.filme_favorito.ValidacaoFavoritar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoFavoritarNotFoundFilmeFavorito implements ValidacaoFavoritar<DTOFavoritar> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Override
    public void validar(DTOFavoritar dto) {
        var usuario = usuarioRepository.findById(dto.idUsuario());
        var filme = filmeRepository.findById(dto.idFilme());
        if(usuario.isEmpty() || filme.isEmpty()){
            throw new NotFoundException("Usuário ou filme não encontrado!");
        }
    }

    @Override
    public Class<DTOFavoritar> getTipo(){
        return DTOFavoritar.class;
    }
}

