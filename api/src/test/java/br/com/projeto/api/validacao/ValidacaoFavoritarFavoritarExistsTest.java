package br.com.projeto.api.validacao;

import br.com.projeto.api.infra.exception.ValidacaoExisteException;
import br.com.projeto.api.modelo.interacoes.favoritar.DTOFavoritar;
import br.com.projeto.api.modelo.interacoes.favoritar.FilmeFavoritoRepository;
import br.com.projeto.api.validacao.filme_favorito.DTOFavoritar.ValidacaoFavoritarExistsFilmeFavorito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ValidacaoFavoritarFavoritarExistsTest {

    @InjectMocks
    private ValidacaoFavoritarExistsFilmeFavorito validacao;

    @Mock
    private FilmeFavoritoRepository filmeFavoritoRepository;

    private DTOFavoritar dtoFilmeFavorito;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startDTO();
    }

    @Test
    @DisplayName("Teste - Validação quando usuário busca por idFilme e idUsuario e sistema acha no banco")
    void ValidacaoFavoritarExistsNaoRetornaErro() {

        when(filmeFavoritoRepository.existsByUsuarioIdAndFilmeId(anyLong(), anyLong())).thenReturn(false);

        assertDoesNotThrow(() -> validacao.validar(dtoFilmeFavorito));
    }

    @Test
    @DisplayName("Teste - Validação quando usuário busca por idFilme e idUsuario e sistema não acha")
    void ValidacaoFavoritarExistsRetornaErro(){
        when(filmeFavoritoRepository.existsByUsuarioIdAndFilmeId(anyLong(), anyLong())).thenReturn(true);
        assertThrows(ValidacaoExisteException.class, () -> validacao.validar(dtoFilmeFavorito));
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