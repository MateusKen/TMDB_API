package br.com.projeto.api.servico;

import br.com.projeto.api.modelo.filme.FilmeRepository;
import br.com.projeto.api.modelo.interacoes.favoritar.*;
import br.com.projeto.api.modelo.usuario.DTOUsuario;
import br.com.projeto.api.modelo.usuario.UsuarioRepository;
import br.com.projeto.api.validacao.filme_favorito.ValidacaoFavoritar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoritarServico {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private FilmeFavoritoRepository filmeFavoritoRepository;

    @Autowired
    private List<ValidacaoFavoritar<?>> validacoes;

    public void favoritar(DTOFavoritar dto){

        validacoes.forEach(v -> {
            if(v.getTipo().isInstance(dto)){
                ValidacaoFavoritar<DTOFavoritar> validacaoFavoritar = (ValidacaoFavoritar<DTOFavoritar>) v;
                validacaoFavoritar.validar(dto);
            }
        });

        var usuario = usuarioRepository.findById(dto.idUsuario()).get();
        var filme = filmeRepository.findById(dto.idFilme()).get();

        var filmeFavorito = FilmeFavorito.builder()
                .usuario(usuario)
                .filme(filme).build();

        filmeFavoritoRepository.save(filmeFavorito);
    }

    public void favoritarAdicionar(DTOFavoritarAdicionar dto){

        validacoes.forEach(v -> {
            if(v.getTipo().isInstance(dto.idFavorito())){
                ValidacaoFavoritar<Long> validacaoFavoritar = (ValidacaoFavoritar<Long>) v;
                validacaoFavoritar.validar(dto.idFavorito());
            }
        });

        var filme = filmeFavoritoRepository.findById(dto.idFavorito()).get();

        var filmeFavorito = FilmeFavorito.builder()
                .id(filme.getId())
                .filme(filme.getFilme())
                .usuario(filme.getUsuario())
                .rating(dto.rating())
                .comment(dto.comment()).build();

        filmeFavoritoRepository.save(filmeFavorito);
    }

    public List<DTOFilmeFavorito> selecionar() {
        List<FilmeFavorito> filmesFavoritos = filmeFavoritoRepository.findAll();

        return filmesFavoritos.stream()
                .map(filmeFavorito -> new DTOFilmeFavorito(
                        filmeFavorito.getId(),
                        filmeFavorito.getFilme(),
                        new DTOUsuario(
                                filmeFavorito.getUsuario().getId(),
                                filmeFavorito.getUsuario().getLogin()),
                        filmeFavorito.getRating(),
                        filmeFavorito.getComment()
                ))
                .toList();
    }

    public DTOFilmeFavorito selecionarPeloId(Long idFilmeFavorito){

        validacoes.forEach(v -> {
            if(v.getTipo().isInstance(idFilmeFavorito)){
                ValidacaoFavoritar<Long> validacaoFavoritar = (ValidacaoFavoritar<Long>) v;
                validacaoFavoritar.validar(idFilmeFavorito);
            }
        });

        var filmeFavorito = filmeFavoritoRepository.findById(idFilmeFavorito).get();

        return DTOFilmeFavorito.builder()
                .id(idFilmeFavorito)
                .filme(filmeFavorito.getFilme())
                .usuario(new DTOUsuario(filmeFavorito.getUsuario().getId(),filmeFavorito.getUsuario().getLogin()))
                .rating(filmeFavorito.getRating())
                .comment(filmeFavorito.getComment())
                .build();
    }
}
