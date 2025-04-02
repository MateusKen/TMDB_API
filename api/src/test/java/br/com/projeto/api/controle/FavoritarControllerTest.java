package br.com.projeto.api.controle;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.infra.exception.ValidacaoExisteException;
import br.com.projeto.api.modelo.interacoes.favoritar.DTOFavoritar;
import br.com.projeto.api.modelo.interacoes.favoritar.DTOFavoritarAdicionar;
import br.com.projeto.api.modelo.interacoes.favoritar.DTOFilmeFavorito;
import br.com.projeto.api.servico.FavoritarServico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class FavoritarControllerTest {

    @InjectMocks
    private FavoritarController favoritarController;

    @Mock
    private FavoritarServico favoritarServico;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Checa se controller cai no created")
    void whenFavoritarReturns201(){
        doNothing().when(favoritarServico).favoritar(any());
        var response = favoritarController.favoritar(new DTOFavoritar(1L, 1L));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se controller cai no notfound")
    void whenFavoritarReturns404(){
        doThrow(new NotFoundException("")).when(favoritarServico).favoritar(any());
        var response = favoritarController.favoritar(new DTOFavoritar(1L, 1L));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se controller cai no conflict")
    void whenFavoritarReturns409(){
        doThrow(new ValidacaoExisteException("")).when(favoritarServico).favoritar(any());
        var response = favoritarController.favoritar(new DTOFavoritar(1L, 1L));
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se controller entra num conflito interno")
    void whenFavoritarReturns500(){
        doThrow(new RuntimeException("")).when(favoritarServico).favoritar(any());
        var response = favoritarController.favoritar(new DTOFavoritar(1L, 1L));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se controller cai no created")
    void whenFavoritarAdicionarReturns200(){
        doNothing().when(favoritarServico).favoritarAdicionar(any());
        var response = favoritarController.favoritarAdicionar(new DTOFavoritarAdicionar(1L, "", 1));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se controller cai no notfound")
    void whenFavoritaraAdicionarReturns404(){
        doThrow(new NotFoundException("")).when(favoritarServico).favoritarAdicionar(any());
        var response = favoritarController.favoritarAdicionar(new DTOFavoritarAdicionar(1L, "", 1));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se controller cai no erro interno")
    void whenFavoritaraAdicionarReturns500(){
        doThrow(new RuntimeException("")).when(favoritarServico).favoritarAdicionar(any());
        var response = favoritarController.favoritarAdicionar(new DTOFavoritarAdicionar(1L, "", 1));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se controller cai no ok")
    void whenSelecionarReturns200(){
        when(favoritarServico.selecionar()).thenReturn(List.of(new DTOFilmeFavorito()));
        var response = favoritarController.selecionar();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se controller cai no created")
    void whenSelecionarPorIdReturns200(){
        when(favoritarServico.selecionarPeloId(anyLong())).thenReturn(new DTOFilmeFavorito());
        var response = favoritarController.selecionarPorId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Checa se controller cai no notfound")
    void whenSelecionarPorIdReturns404(){
        doThrow(new NotFoundException("")).when(favoritarServico).selecionarPeloId(anyLong());
        var response = favoritarController.selecionarPorId(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

//    @Test
//    void whenFavoritarReturnsCreated() throws Exception {
//
//        String json = """
//                {
//                "idUsuario":1,
//                "idFilme":1
//                }
//                """;
//
//        var response = mvc.perform(
//                post("/favoritar")
//                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt()
//                        .content(json)
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andReturn().getResponse();
//
//        assertEquals(201, response.getStatus());
//    }
//
//    @Test
//    void whenFavoritarReturnsNotFound() throws Exception {
//        String json = "";
//
//        var response = mvc.perform(
//                post("/favoritar")
//                        .content(json)
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andReturn().getResponse();
//
//        assertEquals(404, response.getStatus());
//
//    }

//
//    @Test
//    void whenFavoritarReturnsConflict(){
//        when(servico.favoritar(anyLong(),anyLong())).thenReturn(ResponseEntity.status(409).build());
//        ResponseEntity<?> response = controller.favoritar(dados);
//        assertEquals(ResponseEntity.status(HttpStatusCode.valueOf(409)).build().getStatusCode(), response.getStatusCode());
//
//    }
//
//    @Test
//    void whenFavoritoAdicionarReturnsOk() {
//        when(servico.favoritarAdicionar(anyLong(),anyInt(),anyString())).thenReturn(ResponseEntity.status(200).build());
//        ResponseEntity<?> response = controller.favoritarAdicionar(dadosTransferenciaFavoritar);
//        assertEquals(ResponseEntity.status(HttpStatusCode.valueOf(200)).build().getStatusCode(), response.getStatusCode());
//    }
//
//    @Test
//    void whenFavoritarAdicionarReturnsNotFound() {
//        when(servico.favoritarAdicionar(anyLong(),anyInt(),anyString())).thenReturn(ResponseEntity.status(404).build());
//        ResponseEntity<?> response = controller.favoritarAdicionar(dadosTransferenciaFavoritar);
//        assertEquals(ResponseEntity.status(HttpStatusCode.valueOf(404)).build().getStatusCode(), response.getStatusCode());
//    }
//
//    @Test
//    void whenSelecionarReturnsOk() {
//        when(servico.selecionar()).thenReturn(ResponseEntity.status(200).build());
//        ResponseEntity<?> response = controller.selecionar();
//        assertEquals(ResponseEntity.status(HttpStatusCode.valueOf(200)).build().getStatusCode(), response.getStatusCode());
//    }
//
//    @Test
//    void whenSelecionarPorIdReturnsOk() {
//        when(servico.selecionarPeloId(anyLong())).thenReturn(ResponseEntity.status(200).build());
//        ResponseEntity<?> response = controller.selecionarPorId(1L);
//        assertEquals(ResponseEntity.status(HttpStatusCode.valueOf(200)).build().getStatusCode(), response.getStatusCode());
//    }
//
//    @Test
//    void whenSelecionarPorIdReturnNotFound() {
//        when(servico.selecionarPeloId(anyLong())).thenReturn(ResponseEntity.status(404).build());
//        ResponseEntity<?> response = controller.selecionarPorId(1L);
//        assertEquals(ResponseEntity.status(HttpStatusCode.valueOf(404)).build().getStatusCode(), response.getStatusCode());
//    }
}