package br.com.projeto.api;

import br.com.projeto.api.modelo.DTOFilme;
import com.google.gson.Gson;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class TMDBClientTest {

    @InjectMocks
    private TMDBClient tmdbClient;

    @Mock
    private OkHttpClient client;

    @Mock
    private Response response;

    @Mock
    private Call call;

    private Request request;
    private Gson gson;
    private DTOFilme dtoFilme;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startDTOFilme();
        startResponse();
        startRequest();
    }

    @Test
    void whenGetMovieDetailsThenReturnSuccessful() throws IOException {
        when(client.newCall(request)).thenReturn(call);
        when(call.execute()).thenReturn(response);
        when(response.isSuccessful()).thenReturn(true);
        //when(response.body()).thenReturn(dtoFilme);
        Optional<DTOFilme> obj = Optional.of(tmdbClient.getMovieDetails(1));
        assertEquals(DTOFilme.class, obj.getClass());
    }

    @Test
    void whenGetMovieDetailsThenReturnUnsuccessful() {
        when(response.isSuccessful()).thenReturn(false);
        DTOFilme response = tmdbClient.getMovieDetails(1);
        assertEquals(null, response);
    }

    void startDTOFilme() {
        dtoFilme = DTOFilme.builder()
                .title("Teste")
                .overview("Teste")
                .release_date("2021-01-01")
                .vote_average(10.0f)
                .popularity(10.0f)
                .build();
    }

    void startResponse(){
        response = new Response.Builder()
                .code(200) // Status code
                .build();
    }

    void startRequest(){
        request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/1")
                .get()
                .addHeader("Accept", "application/json")
                //.addHeader("Authorization", "Bearer " + tmdbClient.API_KEY)
                .build();
    }
}