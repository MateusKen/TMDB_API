package br.com.projeto.api.validacao;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.modelo.filme.Filme;
import br.com.projeto.api.modelo.filme.FilmeRepository;
import br.com.projeto.api.modelo.interacoes.favoritar.DTOFavoritar;
import br.com.projeto.api.modelo.interacoes.favoritar.FilmeFavorito;
import br.com.projeto.api.modelo.interacoes.favoritar.FilmeFavoritoRepository;
import br.com.projeto.api.modelo.usuario.Usuario;
import br.com.projeto.api.modelo.usuario.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ValidarNotFoundFilmeFavoritoTest {

    @InjectMocks
    private ValidarNotFoundFilmeFavorito validacao;

    @Mock
    private FilmeFavoritoRepository filmeFavoritoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Teste - Usuário procura Filme favorito pelo id e acha")
    void ValidacaoFavoritarExistsNaoRetornaErro() {
        when(filmeFavoritoRepository.findById(anyLong())).thenReturn(Optional.of(new FilmeFavorito()));

        assertDoesNotThrow(() -> validacao.validar(anyLong()));
    }

    @Test
    @DisplayName("Teste - Usuário procura Filme favorito pelo id e não acha")
    void ValidacaoFavoritarExistsRetornaErro(){
        when(filmeFavoritoRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> validacao.validar(anyLong()));
    }

    @Test
    void getTipoDeveRetornarClasseCorreta() {
        assertDoesNotThrow(() -> {
            Class<Long> tipo = validacao.getTipo();
            assert tipo.equals(Long.class);
        });
    }
}