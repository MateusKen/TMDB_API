package br.com.projeto.api.servico;

import br.com.projeto.api.modelo.Filme;
import br.com.projeto.api.repositorio.Repositorio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ServicoTest {

    public static final int ID = 1;

    @InjectMocks
    private Servico servico;

    @Mock
    private Repositorio repositorio;
    private Filme filme;
    private Optional<Filme> optionalFilme;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startFilme();
    }

    @Test
    void whenCadastrarThenReturnCreated() {
        Mockito.when(repositorio.save(Mockito.any())).thenReturn(filme);

        ResponseEntity<?> response = servico.cadastrar(filme);
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(filme), response);
    }

    @Test
    void whenCadastrarThenReturnBadRequest() {
        filme.setTitle("");
        ResponseEntity<?> response = servico.cadastrar(filme);
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).build().getStatusCode(), response.getStatusCode());
    }

    @Test
    void whenCadastrarThenReturnConflict() {
        Mockito.when(repositorio.findByTitle(Mockito.anyString())).thenReturn(filme);
        ResponseEntity<?> response = servico.cadastrar(filme);
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.CONFLICT).build().getStatusCode(), response.getStatusCode());
    }

    @Test
    void whenSelecionarThenReturnOk() {
        Mockito.when(repositorio.findAll()).thenReturn(List.of(filme));

        ResponseEntity<?> response = servico.selecionar();
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).body(List.of(filme)), response);
    }

    @Test
    void whenSelecionarPorIdThenReturnOk() {
        Mockito.when(repositorio.findById(Mockito.anyInt())).thenReturn(filme);
        ResponseEntity<?> response = servico.selecionarPeloId(ID);

        Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).body(filme), response);
    }

    @Test
    void whenSelecionarPorIdThenReturnNotFound() {
        Mockito.when(repositorio.findById(Mockito.anyInt())).thenReturn(null);
        ResponseEntity<?> response = servico.selecionarPeloId(ID);

        Assertions.assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).build().getStatusCode(), response.getStatusCode());
    }

    @Test
    void whenEditarThenReturnNotFound() {
        Mockito.when(repositorio.countById(Mockito.anyInt())).thenReturn(0);
        ResponseEntity<?> response = servico.editar(filme);

        Assertions.assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).build().getStatusCode(), response.getStatusCode());
    }

    @Test
    void whenEditarThenReturnBadRequest() {
        Mockito.when(repositorio.countById(Mockito.anyInt())).thenReturn(1);
        filme.setTitle("");
        ResponseEntity<?> response = servico.editar(filme);
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).build().getStatusCode(), response.getStatusCode());
    }

    @Test
    void whenEditarThenReturnConflict() {
        Mockito.when(repositorio.countById(Mockito.anyInt())).thenReturn(1);
        Mockito.when(repositorio.findByTitle(Mockito.anyString())).thenReturn(filme);
        ResponseEntity<?> response = servico.editar(filme);
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.CONFLICT).build().getStatusCode(), response.getStatusCode());
    }

    @Test
    void whenEditarThenReturnOk() {
        Mockito.when(repositorio.countById(Mockito.anyInt())).thenReturn(1);
        Mockito.when(repositorio.save(Mockito.any())).thenReturn(filme);
        ResponseEntity<?> response = servico.editar(filme);
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).body(filme), response);
    }

    @Test
    void whenRemoverThenReturnNotFound() {
        Mockito.when(repositorio.countById(Mockito.anyInt())).thenReturn(0);
        ResponseEntity<?> response = servico.remover(ID);
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).build().getStatusCode(), response.getStatusCode());
    }

    @Test
    void whenRemoverThenReturnOk() {
        Mockito.when(repositorio.countById(Mockito.anyInt())).thenReturn(1);
        ResponseEntity<?> response = servico.remover(ID);
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build().getStatusCode(), response.getStatusCode());
    }

    @Test
    void whenMostraMaiorNotaThenReturnOk() {
        Mockito.when(repositorio.maiorNota()).thenReturn(filme);
        ResponseEntity<?> response = servico.mostraMaiorNota();
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).body(filme), response);
    }

    @Test
    void whenMostraPopularidadeMaiorQueThenReturnOk() {
        Mockito.when(repositorio.popularidadeMaiorQue(Mockito.anyFloat())).thenReturn(List.of(filme));
        ResponseEntity<?> response = servico.mostraPopularidadeMaiorQue(10);
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).body(List.of(filme)), response);
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
        optionalFilme = Optional.of(Filme.builder()
                .id(ID)
                .title("Fight Club")
                .overview("a fighting club")
                .popularity(10)
                .release_date("10/10")
                .vote_average(10)
                .vote_count(100)
                .build());
    }
}