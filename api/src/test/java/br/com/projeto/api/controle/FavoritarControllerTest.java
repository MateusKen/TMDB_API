//package br.com.projeto.api.controle;
//
//import br.com.projeto.api.modelo.interacoes.favoritar.DTOFavoritar;
//import br.com.projeto.api.modelo.interacoes.favoritar.DadosTransferenciaFavoritar;
//import br.com.projeto.api.servico.FavoritarServico;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class FavoritarControllerTest {
//
//    @InjectMocks
//    private FavoritarController controller;
//
//    @Mock
//    private FavoritarServico servico;
//
//    @Autowired
//    private MockMvc mvc;
//
//    private DTOFavoritar dados;
//    private DadosTransferenciaFavoritar dadosTransferenciaFavoritar;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        startDadosFilme();
//    }
//
//    @Test
//    void whenFavoritarReturnsCreated(){
//        when(servico.favoritar(anyLong(),anyLong())).thenReturn(ResponseEntity.status(201).build());
//        ResponseEntity<?> response = controller.favoritar(dados);
//        assertEquals(ResponseEntity.status(HttpStatusCode.valueOf(201)).build().getStatusCode(), response.getStatusCode());
//
//    }
//
//    @Test
//    void whenFavoritarReturnsNotFound(){
//        when(servico.favoritar(anyLong(),anyLong())).thenReturn(ResponseEntity.status(404).build());
//        ResponseEntity<?> response = controller.favoritar(dados);
//        assertEquals(ResponseEntity.status(HttpStatusCode.valueOf(404)).build().getStatusCode(), response.getStatusCode());
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
//
//    void startDadosFilme(){
//        dados = new DTOFavoritar(1L, 1L);
//        dadosTransferenciaFavoritar = new DadosTransferenciaFavoritar(1L, 1, "Teste");
//    }
//}