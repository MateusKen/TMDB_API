package br.com.projeto.api.validacao;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.modelo.filme.Filme;
import br.com.projeto.api.modelo.filme.FilmeRepository;
import br.com.projeto.api.modelo.interacoes.favoritar.DTOFavoritar;
import br.com.projeto.api.modelo.usuario.Usuario;
import br.com.projeto.api.modelo.usuario.UsuarioRepository;
import br.com.projeto.api.validacao.filme_favorito.DTOFavoritar.ValidacaoFavoritarNotFoundFilmeFavorito;
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

class ValidacaoFavoritarNotFoundFilmeFavoritoFavoritarTest {

    @InjectMocks
    private ValidacaoFavoritarNotFoundFilmeFavorito validacao;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private FilmeRepository filmeRepository;

    private DTOFavoritar dtoFilmeFavorito;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startDTO();
    }

    @Test
    @DisplayName("Teste - Prcoura filme e usuário pelos IDs e acha")
    void ValidacaoFavoritarExistsNaoRetornaErro() {
        when(filmeRepository.findById(anyLong())).thenReturn(Optional.of(new Filme()));
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(new Usuario()));

        assertDoesNotThrow(() -> validacao.validar(dtoFilmeFavorito));
    }

    @Test
    @DisplayName("Teste - Procura filme e usuário pelos IDs e não acha")
    void ValidacaoFavoritarExistsRetornaErro(){
        when(filmeRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> validacao.validar(dtoFilmeFavorito));
    }

    @Test
    void getTipoDeveRetornarClasseCorreta() {
        assertDoesNotThrow(() -> {
            Class<DTOFavoritar> tipo = validacao.getTipo();
            assert tipo.equals(DTOFavoritar.class);
        });
    }

    void startDTO(){
        dtoFilmeFavorito = new DTOFavoritar(1L, 1L);
    }
}