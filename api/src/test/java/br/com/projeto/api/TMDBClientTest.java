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

    private Gson gson;
    private DTOFilme dtoFilme;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startDTOFilme();
    }

    @Test
    void whenGetMovieDetailsThenReturnSuccessful() throws IOException {
        when(client.newCall(any(Request.class))).thenReturn(call);
        when(call.execute()).thenReturn(response);
        //when(response.isSuccessful()).thenReturn(true);
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
                .request(new Request.Builder().url("https://example.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200) // Status code
                .message("OK") // Status message
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        "{\"key\":\"value\"}" // Mocked JSON response body
                ))
                .build();
    }
}