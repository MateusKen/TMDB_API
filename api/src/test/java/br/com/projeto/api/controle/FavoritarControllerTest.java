package br.com.projeto.api.controle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class FavoritarControllerTest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
    }

//    @Test
//    @WithMockUser
//    void favoritar() throws  Exception{
//        var response = mvc.perform(post("/favoritar")
//                .andReturn()
//                .getResponse());
//        assertEquals(400, response.getStatus());
//    }

    @Test
    void favoritarAdicionar() {
    }

    @Test
    void selecionar() {
    }

    @Test
    void selecionarPorId() {
    }
}