package br.com.projeto.api.servico;

import br.com.projeto.api.modelo.filme.FilmeRepository;
import br.com.projeto.api.modelo.interacoes.favoritar.DTOFilmeFavorito;
import br.com.projeto.api.modelo.interacoes.favoritar.FilmeFavorito;
import br.com.projeto.api.modelo.interacoes.favoritar.FilmeFavoritoRepository;
import br.com.projeto.api.modelo.usuario.UsuarioRepository;
import br.com.projeto.api.resources.exceptions.StandardError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FavoritarServico {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private FilmeFavoritoRepository filmeFavoritoRepository;

    public ResponseEntity<?> favoritar(Long idUsuario, Long idFilme){
        var usuario = usuarioRepository.findById(idUsuario);
        var filme = filmeRepository.findById(idFilme);
        var obj = filmeFavoritoRepository.existsByUsuarioIdAndFilmeId(idUsuario,idFilme);
        if(usuario.isEmpty() || filme.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar um dos campos pedidos");
        }
        if(obj){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("O usuário já favoritou esse filme!");
        }
        var filmeFavorito = FilmeFavorito.builder()
                .usuario(usuario.get())
                .filme(filme.get()).build();

        filmeFavoritoRepository.save(filmeFavorito);
        return new ResponseEntity<>("Filme favorito criado com sucesso!", HttpStatus.CREATED);
    }

    public ResponseEntity<?> favoritarAdicionar(Long idFilmeFavorito, Integer rating, String comment){
        var filme = filmeFavoritoRepository.findById(idFilmeFavorito);
        System.out.println(idFilmeFavorito);
        if(filme.isEmpty()){
            StandardError error = StandardError.NotFound();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }else{
            var filmeFavorito = FilmeFavorito.builder()
                    .id(idFilmeFavorito)
                    .filme(filme.get().getFilme())
                    .usuario(filme.get().getUsuario())
                    .rating(rating)
                    .comment(comment).build();
            filmeFavoritoRepository.save(filmeFavorito);
            return new ResponseEntity<>("Filme favorito editado com sucesso!", HttpStatus.OK);
        }
    }

    public ResponseEntity<?> selecionar(){
        return new ResponseEntity<>(filmeFavoritoRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> selecionarPeloId(Long id){
        var filmeFavorito = filmeFavoritoRepository.findById(id);
        if(filmeFavorito.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme favoritado não encontrado!");
        }
        DTOFilmeFavorito dtoFilme = DTOFilmeFavorito.builder()
                .id(id)
                .filme(filmeFavorito.get().getFilme())
                .idUsuario(filmeFavorito.get().getUsuario().getId())
                .loginUsuario(filmeFavorito.get().getUsuario().getLogin())
                .rating(filmeFavorito.get().getRating())
                .comment(filmeFavorito.get().getComment())
                .build();
        return new ResponseEntity<>(dtoFilme, HttpStatus.OK);
    }
}
