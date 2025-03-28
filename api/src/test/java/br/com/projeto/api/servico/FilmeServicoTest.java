package br.com.projeto.api.servico;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.infra.exception.ValidacaoCampoObrigatorioException;
import br.com.projeto.api.infra.exception.ValidacaoExisteException;
import br.com.projeto.api.modelo.filme.Filme;
import br.com.projeto.api.modelo.filme.FilmeRepository;
import br.com.projeto.api.modelo.interacoes.favoritar.DTOFavoritar;
import br.com.projeto.api.modelo.interacoes.favoritar.DTOFilmeFavorito;
import br.com.projeto.api.modelo.interacoes.favoritar.FilmeFavorito;
import br.com.projeto.api.validacao.filme.ValidacaoFilme;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class FilmeServicoTest {

    public static final Long ID = 1L;

    @InjectMocks
    private FilmeServico filmeServico;

    @Mock
    private FilmeRepository filmeRepository;

    @Mock
    private ValidacaoFilme<Filme> filmeValidacaoMock;

    @Mock
    private ValidacaoFilme<Long> longValidacaoMock;

    private Filme filme;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<ValidacaoFilme<?>> validacoes = List.of(filmeValidacaoMock, longValidacaoMock);
        ReflectionTestUtils.setField(filmeServico, "validacoes", validacoes);

        when(filmeValidacaoMock.getTipo()).thenReturn(Filme.class);
        when(longValidacaoMock.getTipo()).thenReturn(Long.class);
        startFilme();
    }

    @Test
    @DisplayName("Teste para metodo cadastrar, deve chamar metodo .save do repository")
    void whenCadastrarCreates() {
        doNothing().when(filmeValidacaoMock).validar(any());
        filmeServico.cadastrar(filme);
        verify(filmeRepository, times(1)).save(any(Filme.class));
    }

    @Test
    @DisplayName("Teste para metodo cadastrar, deve lançar exception NotFound")
    void whenCadastrarThrowsNotFoundException() {
        doThrow(new NotFoundException("")).when(filmeValidacaoMock).validar(any());
        assertThrows(NotFoundException.class, ()->filmeServico.cadastrar(filme));
    }

    @Test
    @DisplayName("Teste para metodo cadastrar, deve lançar exception ValidacaoCampoObrigatorio")
    void whenCadastrarThrowsCampoObrigatorioException() {
        doThrow(new ValidacaoCampoObrigatorioException("")).when(filmeValidacaoMock).validar(any());
        assertThrows(ValidacaoCampoObrigatorioException.class, ()->filmeServico.cadastrar(filme));
    }

    @Test
    @DisplayName("Teste para metodo cadastrar, deve lançar exception ValidacaoExiste")
    void whenCadastrarThenReturnConflict() {
        doThrow(new ValidacaoExisteException("")).when(filmeValidacaoMock).validar(any());
        assertThrows(ValidacaoExisteException.class, ()->filmeServico.cadastrar(filme));
    }

    @Test
    @DisplayName("Teste para metodo selecionar, deve retornar lista de filmes")
    void whenSelecionarThenListOfFilmes() {
        when(filmeRepository.findAll()).thenReturn(List.of(filme));
        List<Filme> filmes = filmeServico.selecionar();
        assertEquals(Filme.class, filmes.get(0).getClass());
    }

    @Test
    @DisplayName("Teste para metodo selecionarPorId, deve retornar filme")
    void whenSelecionarPorIdThenReturnOk() {
        doNothing().when(longValidacaoMock).validar(any());
        when(filmeRepository.findById(anyLong())).thenReturn(Optional.of(filme));
        Filme filmeMock = filmeServico.selecionarPeloId(1L);
        assertEquals(Filme.class, filmeMock.getClass());
    }

    @Test
    @DisplayName("Teste para metodo selecionarPorId, deve lançar exception NotFound")
    void whenSelecionarPorIdThenReturnNotFound() {
        doThrow(new NotFoundException("")).when(longValidacaoMock).validar(any());
        assertThrows(NotFoundException.class, ()->filmeServico.selecionarPeloId(1L));
    }

    @Test
    @DisplayName("Teste para metodo editar, deve lançar exception NotFound")
    void whenEditarThenReturnNotFound() {
        doThrow(new NotFoundException("")).when(filmeValidacaoMock).validar(any());
        assertThrows(NotFoundException.class, ()->filmeServico.cadastrar(filme));
    }

    @Test
    @DisplayName("Teste para metodo editar, deve lançar exception ValidacaoCampoObrigatorio")
    void whenEditarThenReturnBadRequest() {
        doThrow(new ValidacaoCampoObrigatorioException("")).when(filmeValidacaoMock).validar(any());
        assertThrows(ValidacaoCampoObrigatorioException.class, ()-> filmeServico.cadastrar(filme));
    }

    @Test
    @DisplayName("Teste para metodo editar, recebe um novo filme, verifica e o salva")
    void whenEditarThenReturnOk() {
        doNothing().when(filmeValidacaoMock).validar(any());
        filmeServico.editar(filme);
        verify(filmeRepository, times(1)).save(any(Filme.class));
    }

    @Test
    @DisplayName("Teste para metodo remover, deve lançar exception NotFound")
    void whenRemoverThenReturnNotFound() {
        doThrow(new NotFoundException("")).when(longValidacaoMock).validar(any());
        assertThrows(NotFoundException.class, ()-> filmeServico.remover(1L));
    }

    @Test
    @DisplayName("Teste para metodo remover, deve remover o filme")
    void whenRemoverThenReturnNoContent() {
        doNothing().when(filmeValidacaoMock).validar(any());
        filmeServico.remover(ID);
        verify(filmeRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Teste para metodo mostraMaiorNota, deve retornar filme com maior nota")
    void whenMostraMaiorNotaThenFilme() {
        when(filmeRepository.maiorNota()).thenReturn(filme);
        var filmeMock = filmeServico.mostraMaiorNota();
        assertEquals(filme, filmeMock);
    }

    @Test
    @DisplayName("Teste para metodo mostraPopularidadeMaiorQue, retorna lista de filmes com nota maior que n")
    void whenMostraPopularidadeMaiorQueThenReturnOk() {
        when(filmeRepository.popularidadeMaiorQue(anyFloat())).thenReturn(List.of(filme));
        var filmesMock = filmeServico.mostraPopularidadeMaiorQue(anyFloat());
        assertEquals(Filme.class, filmesMock.get(0).getClass());
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
}