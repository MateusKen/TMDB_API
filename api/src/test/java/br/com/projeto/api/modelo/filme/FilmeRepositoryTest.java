package br.com.projeto.api.modelo.filme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class FilmeRepositoryTest {

    public static final Long ID = 1L;

    @Mock
    private FilmeRepository filmeRepository;

    @Autowired
    private TestEntityManager em;

    private Filme filme;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startFilme();
    }

    @Test
    void findAll() {
        when(filmeRepository.findAll()).thenReturn(List.of(Filme.builder().build()));
        assertEquals(1, filmeRepository.findAll().size());
    }

    @Test
    void findById() {
        when(filmeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(filme));
        assertThat(filmeRepository.findById(ID)).isPresent();
    }

    @Test
    void findByTitle() {
        when(filmeRepository.findByTitle(anyString())).thenReturn(filme);

        var filmeTest = filmeRepository.findByTitle("Fight Club");
        assertEquals(filme, filmeTest);
    }

    @Test
    void maiorNota() {
        when(filmeRepository.maiorNota()).then(returns -> filme);
        var filmeTest = filmeRepository.maiorNota();

        assertEquals(filme, filmeTest);
    }

    @Test
    void popularidadeMaiorQue() {
        when(filmeRepository.popularidadeMaiorQue(anyFloat())).thenReturn(List.of(filme));
        var filmesTest = filmeRepository.popularidadeMaiorQue(10);
        assertEquals(1, filmesTest.size());
    }

    @Test
    void countById() {
        when(filmeRepository.countById(anyLong())).then(returns -> 1);
        var filmeTest = filmeRepository.countById(ID);
        assertEquals(1, filmeTest);
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