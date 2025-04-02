package br.com.projeto.api.controle;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.infra.exception.ValidacaoCampoObrigatorioException;
import br.com.projeto.api.infra.exception.ValidacaoExisteException;
import br.com.projeto.api.modelo.filme.Filme;
import br.com.projeto.api.servico.FilmeServico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ControleTest {

    public static final Long ID = 1L;

    @InjectMocks
    private FilmeControle filmeControle;

    @Mock
    private FilmeServico filmeServico;

    private Filme filme;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startFilme();
    }

    @Test
    @DisplayName("Checa se cadastrar retorna created")
    void whenCadastrarThenReturn201() {
        doNothing().when(filmeServico).cadastrar(any());
        var response = filmeControle.cadastrar(filme);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se cadastrar retorna conflict")
    void whenCadastrarThenReturn409() {
        doThrow(new ValidacaoExisteException("")).when(filmeServico).cadastrar(any());
        var response = filmeControle.cadastrar(filme);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se cadastrar retorna bad request")
    void whenCadastrarThenReturn400() {
        doThrow(new ValidacaoCampoObrigatorioException("")).when(filmeServico).cadastrar(any());
        var response = filmeControle.cadastrar(filme);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se cadastrar retorna bad request")
    void whenCadastrarThenReturn500() {
        doThrow(new RuntimeException("")).when(filmeServico).cadastrar(any());
        var response = filmeControle.cadastrar(filme);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se selecionar retorna ok")
    void whenSelecionarThenReturn200() {
        when(filmeServico.selecionar()).thenReturn(List.of(new Filme()));
        var response = filmeControle.selecionar();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se selecionarPorId retorna ok")
    void whenSelecionarPorIdThenReturn200() {
        when(filmeServico.selecionarPeloId(anyLong())).thenReturn(new Filme());
        var response = filmeControle.selecionarPeloId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se selecionarPorId retorna notfound")
    void whenSelecionarPorIdThenReturn404() {
        doThrow(new NotFoundException("")).when(filmeServico).selecionarPeloId(anyLong());
        var response = filmeControle.selecionarPeloId(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se editar retorna ok")
    void wheneEditarThenReturn200() {
        doNothing().when(filmeServico).editar(any());
        var response = filmeControle.editar(filme);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se editar retorna bad request")
    void whenEditarThenReturn400() {
        doThrow(new ValidacaoCampoObrigatorioException("")).when(filmeServico).editar(any());
        var response = filmeControle.editar(filme);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se editar retorna notfound")
    void whenEditarThenReturn404() {
        doThrow(new NotFoundException("")).when(filmeServico).editar(any());
        var response = filmeControle.editar(filme);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se remover retorna notfound")
    void whenRemoverThenReturn204() {
        doNothing().when(filmeServico).remover(any());
        var response = filmeControle.remover(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se remover retorna notfound")
    void whenRemoverThenReturn404() {
        doThrow(new NotFoundException("")).when(filmeServico).remover(any());
        var response = filmeControle.remover(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se mostraMaiorNota retorna ok")
    void whenMostraMaiorNotaThenReturn200() {
        when(filmeServico.mostraMaiorNota()).thenReturn(filme);
        var response = filmeControle.maiorNota();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se popularidadeMaiorQue retorna ok")
    void whenPopularidadeMaiorQueThenReturn200() {
        when(filmeServico.mostraPopularidadeMaiorQue(anyFloat())).thenReturn(List.of(filme));
        var response = filmeControle.popularidadeMaiorQue(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
//
//    @Test
//    void whenSelecionarReturnOk() {
//        when(filmeServico.selecionar()).thenReturn(new ResponseEntity<>(HttpStatus.OK));
//        ResponseEntity<?> response = controle.selecionar();
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void mensagem() {
//        assertEquals("Hello World 1", controle.mensagem());
//    }
//
//    @Test
//    void whenSelecionarPeloIdThenReturnOk() {
//        when(filmeServico.selecionarPeloId(anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
//        ResponseEntity<?> response = controle.selecionarPeloId(filme.getId());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void whenEditarThenReturnOk() {
//        when(filmeServico.editar(any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
//        ResponseEntity<?> response = controle.editar(filme);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void whenRemoverThenReturnOk() {
//        when(filmeServico.remover(anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
//        ResponseEntity<?> response = controle.remover(filme.getId());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void whenMaiorNotaThenReturnOk() {
//        when(filmeServico.mostraMaiorNota()).thenReturn(new ResponseEntity<>(HttpStatus.OK));
//        ResponseEntity<?> response = controle.maiorNota();
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void whenPopularidadeMaiorQueThenReturnOk() {
//        when(filmeServico.mostraPopularidadeMaiorQue(anyFloat())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
//        ResponseEntity<?> response = controle.popularidadeMaiorQue(anyFloat());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
    @Test
    void status() {
        assertEquals(HttpStatus.CREATED, filmeControle.status().getStatusCode());
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