package br.com.projeto.api.validacao.filme.Filme;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.infra.exception.ValidacaoCampoObrigatorioException;
import br.com.projeto.api.modelo.filme.Filme;
import br.com.projeto.api.modelo.filme.FilmeRepository;
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

class ValidacaoTituloVazioTest {

    @InjectMocks
    private ValidacaoTituloVazio validacao;

    private Filme filme;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startFilme();
    }

    @Test
    @DisplayName("Teste - verifica se titulo esta vazio, não lança exception")
    void ValidacaoCampoObrigatorioNaoRetornaErro() {

        assertDoesNotThrow(() -> validacao.validar(filme));
    }

    @Test
    @DisplayName("Teste - verifica se titulo esta vazio, lança exception")
    void ValidacaoCampoObrigatorioRetornaErro(){
        filme.setTitle("");
        assertThrows(ValidacaoCampoObrigatorioException.class, () -> validacao.validar(filme));
    }

    @Test
    void getTipoDeveRetornarClasseCorreta() {
        assertDoesNotThrow(() -> {
            Class<Filme> tipo = validacao.getTipo();
            assert tipo.equals(Filme.class);
        });
    }

    void startFilme(){
        filme = Filme.builder()
                .id(1L)
                .title("a")
                .overview("a")
                .release_date("a")
                .vote_average(1)
                .popularity(1)
                .vote_count(1).build();
    }
}