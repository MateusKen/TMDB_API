package br.com.projeto.api.servico;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.infra.exception.ValidacaoExisteException;
import br.com.projeto.api.modelo.filme.Filme;
import br.com.projeto.api.modelo.filme.FilmeRepository;
import br.com.projeto.api.modelo.interacoes.favoritar.*;
import br.com.projeto.api.modelo.usuario.Usuario;
import br.com.projeto.api.modelo.usuario.UsuarioRepository;
import br.com.projeto.api.validacao.filme_favorito.ValidacaoFavoritar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class FavoritarServicoTest {

    @InjectMocks
    private FavoritarServico favoritarServico;

    @Mock
    private FilmeFavoritoRepository filmeFavoritoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private FilmeRepository filmeRepository;

    @Mock
    private ValidacaoFavoritar<DTOFavoritar> DTOFavoritarValidacaoFavoritarMock;

    @Mock
    private ValidacaoFavoritar<Long> longValidacaoFavoritarMock;

    public static final Long ID = 1L;

    private Filme filme;
    private Usuario usuario;
    private FilmeFavorito filmeFavorito;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<ValidacaoFavoritar<?>> validacoes = List.of(DTOFavoritarValidacaoFavoritarMock, longValidacaoFavoritarMock);
        ReflectionTestUtils.setField(favoritarServico, "validacoes", validacoes);

        when(DTOFavoritarValidacaoFavoritarMock.getTipo()).thenReturn(DTOFavoritar.class);
        when(longValidacaoFavoritarMock.getTipo()).thenReturn(Long.class);

        startFilme();
        startUsuario();
        startFilmeFavorito();
    }

    @Test
    @DisplayName("Verifica se função entra na exception NotFoundException")
    void whenFavoritarThrowsNotFound() {
        doThrow(new NotFoundException("Erro na validação")).when(DTOFavoritarValidacaoFavoritarMock).validar(any());
        assertThrows(NotFoundException.class, () -> favoritarServico.favoritar(new DTOFavoritar(1L,1L)));
    }

    @Test
    @DisplayName("Verifica se método entra na exception ValidacaoExisteException")
    void whenFavoritarThrowsConflict() {
        doThrow(new ValidacaoExisteException("")).when(DTOFavoritarValidacaoFavoritarMock).validar(any());
        assertThrows(ValidacaoExisteException.class, () -> favoritarServico.favoritar(new DTOFavoritar(1L,1L)));
    }

    @Test
    @DisplayName("Envia os dois IDs pedidos e verifica se .save() é chamada ao final")
    void whenFavoritarSaves(){
        doNothing().when(DTOFavoritarValidacaoFavoritarMock).validar(any());
        when(filmeRepository.findById(anyLong())).thenReturn(Optional.of(filme));
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        favoritarServico.favoritar(new DTOFavoritar(1L, 1L));
        verify(filmeFavoritoRepository, times(1)).save(any(FilmeFavorito.class));
    }

    @Test
    @DisplayName("Verifica se entra na exception NotFoundException")
    void whenFavoritoAdicionarThrowsNotFound() {
        doThrow(new NotFoundException("")).when(longValidacaoFavoritarMock).validar(any());
        assertThrows(NotFoundException.class, () -> favoritarServico.favoritarAdicionar(new DTOFavoritarAdicionar(1L, "", 1)));
    }

    @Test
    @DisplayName("Verifica se .save() é chamada no final")
    void whenFavoritoAdicionarSaves() {
        doNothing().when(longValidacaoFavoritarMock).validar(any());
        when(filmeFavoritoRepository.findById(anyLong())).thenReturn(Optional.of(filmeFavorito));
        favoritarServico.favoritarAdicionar(new DTOFavoritarAdicionar(1L, "a", 1));
        verify(filmeFavoritoRepository, times(1)).save(any(FilmeFavorito.class));
    }

    @Test
    @DisplayName("É para retornar uma lista de DTOFilmeFavorito ao final")
    void whenSelecionarListOfDTOFilmeFavorito() {
        when(filmeFavoritoRepository.findAll()).thenReturn(List.of(filmeFavorito));
        List<DTOFilmeFavorito> filmesFavoritos = favoritarServico.selecionar();
        assertEquals(DTOFilmeFavorito.class, filmesFavoritos.get(0).getClass());
    }

    @Test
    @DisplayName("Chama método selecionarPorId e verifica se lança exception NotFoundException")
    void whenSelecionarPorIdReturnsNotFound() {
        doThrow(new NotFoundException("")).when(longValidacaoFavoritarMock).validar(any());
        assertThrows(NotFoundException.class, ()->favoritarServico.selecionarPeloId(1L));
    }

    @Test
    @DisplayName("Verifica se selecionarPeloId retorna um DTOFilmeFavorito")
    void whenSelecionarPorIdReturnsOk(){
        doNothing().when(longValidacaoFavoritarMock).validar(any());
        when(filmeFavoritoRepository.findById(anyLong())).thenReturn(Optional.of(filmeFavorito));
        DTOFilmeFavorito filmeFavoritoMock = favoritarServico.selecionarPeloId(1L);
        assertEquals(DTOFilmeFavorito.class, filmeFavoritoMock.getClass());
    }

    private void startFilme(){
        filme = Filme.builder()
                .id(ID)
                .title("Fight Club")
                .overview("a fighting club")
                .popularity(10)
                .release_date("10/10")
                .vote_average(10)
                .vote_count(100)
                .build();
    }

    private void startUsuario(){
        usuario = new Usuario(1L,"admin", "admin");
    }

    private void startFilmeFavorito(){
        filmeFavorito = FilmeFavorito.builder()
                .id(ID)
                .usuario(usuario)
                .filme(filme)
                .rating(10)
                .comment("comment")
                .build();
    }
}