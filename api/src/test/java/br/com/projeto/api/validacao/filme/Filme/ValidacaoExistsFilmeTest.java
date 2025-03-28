package br.com.projeto.api.validacao.filme.Filme;

import br.com.projeto.api.infra.exception.ValidacaoExisteException;
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

class ValidacaoExistsFilmeTest {

    @InjectMocks
    private ValidacaoExistsFilme validacao;

    @Mock
    private FilmeRepository filmeRepository;

    private Filme filme;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startFilme();
    }

    @Test
    @DisplayName("Teste - Validação procura filme por id e acha")
    void ValidacaoFilmeExistsNaoRetornaErro() {

        when(filmeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> validacao.validar(filme));
    }

    @Test
    @DisplayName("Teste - Validação procura filme por id e não acha")
    void ValidacaoFilmeExistsRetornaErro(){
        when(filmeRepository.findById(anyLong())).thenReturn(Optional.of(filme));
        assertThrows(ValidacaoExisteException.class, () -> validacao.validar(filme));
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