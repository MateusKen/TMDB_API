package br.com.projeto.api.validacao.filme.Long;

import br.com.projeto.api.infra.exception.NotFoundException;
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

class ValidacaoNotFoundByIdTest {

    @InjectMocks
    private ValidacaoNotFoundById validacao;

    @Mock
    private FilmeRepository filmeRepository;

    private Long id = 1L;
    private Filme filme;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startFilme();
    }

    @Test
    @DisplayName("Teste - Validação quando usuário busca por idFilme e sistema acha no banco")
    void ValidacaoNotFoundByIdNaoRetornaErro() {

        when(filmeRepository.findById(anyLong())).thenReturn(Optional.of(filme));

        assertDoesNotThrow(() -> validacao.validar(id));
    }

    @Test
    @DisplayName("Teste - Validação quando usuário busca por idFilme e sistema não acha")
    void ValidacaoNotFoundByIdRetornaErro(){
        when(filmeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> validacao.validar(id));
    }

    @Test
    void getTipoDeveRetornarClasseCorreta() {
        assertDoesNotThrow(() -> {
            Class<Long> tipo = validacao.getTipo();
            assert tipo.equals(Long.class);
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