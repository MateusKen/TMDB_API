package br.com.projeto.api.controle;

import br.com.projeto.api.modelo.Filme;
import br.com.projeto.api.servico.Servico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ControleTest {

    public static final int ID = 1;

    @InjectMocks
    private Controle controle;

    @Mock
    private Servico servico;

    private Filme filme;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startFilme();
    }

    @Test
    void whenCadastrarThenReturnOk() {
        when(servico.cadastrar(any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        ResponseEntity<?> response = controle.cadastrar(filme);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void whenSelecionarReturnOk() {
        when(servico.selecionar()).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        ResponseEntity<?> response = controle.selecionar();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void mensagem() {
        assertEquals("Hello World 1", controle.mensagem());
    }

    @Test
    void whenSelecionarPeloIdThenReturnOk() {
        when(servico.selecionarPeloId(anyInt())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        ResponseEntity<?> response = controle.selecionarPeloId(filme.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenEditarThenReturnOk() {
        when(servico.editar(any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        ResponseEntity<?> response = controle.editar(filme);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenRemoverThenReturnOk() {
        when(servico.remover(anyInt())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        ResponseEntity<?> response = controle.remover(filme.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenMaiorNotaThenReturnOk() {
        when(servico.mostraMaiorNota()).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        ResponseEntity<?> response = controle.maiorNota();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenPopularidadeMaiorQueThenReturnOk() {
        when(servico.mostraPopularidadeMaiorQue(anyFloat())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        ResponseEntity<?> response = controle.popularidadeMaiorQue(anyFloat());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void status() {
        assertEquals(HttpStatus.CREATED, controle.status().getStatusCode());
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