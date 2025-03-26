package br.com.projeto.api.servico;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.infra.exception.ValidacaoExisteException;
import br.com.projeto.api.modelo.filme.Filme;
import br.com.projeto.api.modelo.filme.FilmeRepository;
import br.com.projeto.api.modelo.interacoes.favoritar.DTOFavoritar;
import br.com.projeto.api.modelo.interacoes.favoritar.FilmeFavorito;
import br.com.projeto.api.modelo.interacoes.favoritar.FilmeFavoritoRepository;
import br.com.projeto.api.modelo.usuario.Usuario;
import br.com.projeto.api.modelo.usuario.UsuarioRepository;
import br.com.projeto.api.validacao.ValidacaoFavoritar;
import br.com.projeto.api.validacao.ValidacaoFavoritarExists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    private List<ValidacaoFavoritar<DTOFavoritar>> validacoes;

    @Mock
    private FilmeRepository filmeRepository;

    public static final Long ID = 1L;

    private Filme filme;
    private Usuario usuario;
    private FilmeFavorito filmeFavorito;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startFilme();
        startUsuario();
        startFilmeFavorito();
    }

    @Test
    void whenFavoritarReturnsNotFound() {

        doAnswer(invocation -> {
            throw new NotFoundException("Erro na validação");
        }).when(validacoes).forEach(any());

        assertThrows(NotFoundException.class, () -> favoritarServico.favoritar(new DTOFavoritar(1L,1L)));
    }

    @Test
    void whenFavoritarReturnsConflict() {
        doAnswer(invocation -> {
            throw new ValidacaoExisteException("Erro na validação");
        }).when(validacoes).forEach(any());

        assertThrows(ValidacaoExisteException.class, () -> favoritarServico.favoritar(new DTOFavoritar(1L,1L)));
    }

    @Test
    void whenFavoritarReturnsCreated(){

        doNothing().when(validacoes).forEach(any());

        when(filmeRepository.findById(anyLong())).thenReturn(Optional.of(filme));
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        verify(filmeFavoritoRepository, times(1)).save(any(FilmeFavorito.class));
    }
//
//    @Test
//    void whenFavoritoAdicionarReturnsNotFound() {
//        when(filmeFavoritoRepository.findById(anyLong())).thenReturn(Optional.empty());
//        var response = favoritarServico.favoritarAdicionar(ID, 10, "comment");
//        assertEquals(404, response.getStatusCodeValue());
//    }
//
//    @Test
//    void whenFavoritoAdicionarReturnsOk() {
//        when(filmeFavoritoRepository.findById(anyLong())).thenReturn(Optional.of(filmeFavorito));
//        var response = favoritarServico.favoritarAdicionar(ID, 10, "comment");
//        assertEquals(200, response.getStatusCodeValue());
//    }
//
//    @Test
//    void whenSelecionarReturnsOk() {
//        when(filmeFavoritoRepository.findAll()).thenReturn(List.of(filmeFavorito));
//        var response = favoritarServico.selecionar();
//        assertEquals(200, response.getStatusCodeValue());
//    }
//
//    @Test
//    void whenSelecionarPorIdReturnsNotFound() {
//        when(filmeFavoritoRepository.findById(anyLong())).thenReturn(Optional.empty());
//        var response = favoritarServico.selecionarPeloId(ID);
//        assertEquals(404, response.getStatusCodeValue());
//    }
//
//    @Test
//    void whenSelecionarPorIdReturnsOk(){
//        when(filmeFavoritoRepository.findById(anyLong())).thenReturn(Optional.of(filmeFavorito));
//        var response = favoritarServico.selecionarPeloId(ID);
//        assertEquals(200, response.getStatusCodeValue());
//    }
//
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