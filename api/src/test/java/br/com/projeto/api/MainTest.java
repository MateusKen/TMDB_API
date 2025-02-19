package br.com.projeto.api;

import br.com.projeto.api.modelo.DTOFilme;
import br.com.projeto.api.modelo.Filme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MainTest {

    @InjectMocks
    private Main main;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TMDBClient tmdbClient;

    @Mock
    private Scanner scanner;

    private Filme filme;
    private DTOFilme dtoFilme;
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startFilme();
    }

    @Test
    void testCadastrarSuccess() {
        // Step 1: Mock dependencies
        TMDBClient mockTmdbClient = mock(TMDBClient.class);
        RestTemplate mockRestTemplate = mock(RestTemplate.class);

        // Step 2: Simulate user input
        String simulatedInput = "123\n"; // User enters "123" as the movie ID
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Step 3: Capture console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Step 4: Mock TMDBClient.getMovieDetails(id)
        DTOFilme mockFilme = new DTOFilme();
        mockFilme.setTitle("Inception");
        mockFilme.setOverview("A mind-bending thriller.");
        when(mockTmdbClient.getMovieDetails(anyInt())).thenReturn(mockFilme);

        // Step 5: Mock RestTemplate.postForObject()
        String mockResponse = "Filme cadastrado com sucesso! ID: 123";
        when(mockRestTemplate.postForObject(anyString(), any(DTOFilme.class), eq(String.class)))
                .thenReturn(mockResponse);

        // Step 6: Create instance of FilmeService with mocked dependencies
        Scanner scanner = new Scanner(System.in);
        String url = "http://example.com/api/filmes";
        FilmeService filmeService = new FilmeService(scanner, mockTmdbClient, mockRestTemplate, url);

        // Step 7: Call the method under test
        filmeService.cadastrar();

        // Step 8: Verify the output
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Digite o código do filme pelo TMDB:"));
        assertTrue(consoleOutput.contains("Filme cadastrado com sucesso!"));
        assertTrue(consoleOutput.contains("Dados do filme " + mockResponse));

        // Step 9: Verify interactions with mocks
        verify(mockTmdbClient, times(1)).getMovieDetails(123);
        verify(mockRestTemplate, times(1)).postForObject(eq(url), eq(mockFilme), eq(String.class));

        // Step 10: Clean up
        System.setIn(System.in);
        System.setOut(System.out);
    }

//    @Test
//    void whenCadastrarThenReturnUnsuccess(){
//        when(scanner.nextInt()).thenReturn(anyInt());
//        when(scanner.nextLine()).thenReturn(anyString());
//        when(tmdbClient.getMovieDetails(anyInt())).thenReturn(dtoFilme);
//        when(restTemplate.postForObject(any(),any(),any())).thenThrow(new Exception());
//        assertEquals();
//    }

    @Test
    void selecionar() {
    }

    @Test
    void selecionarPorId() {
    }

    @Test
    void editar() {
    }

    @Test
    void remover() {
    }

    @Test
    void maiorNota() {
    }

    @Test
    void buscaPorPopularidade() {
    }

    @Test
    void menu() {
    }

    @Test
    void printFilme() {
    }

    @Test
    void main() {
    }

    void startFilme(){
        filme = Filme.builder()
                .id(1)
                .title("Filme")
                .overview("Overview")
                .release_date("2021-01-01")
                .vote_count(1)
                .vote_average(1)
                .popularity(1)
                .build();


        dtoFilme = DTOFilme.builder()
                .title("Filme")
                .overview("Overview")
                .release_date("2021-01-01")
                .vote_count(1)
                .vote_average(1)
                .popularity(1)
                .build();
    }
}