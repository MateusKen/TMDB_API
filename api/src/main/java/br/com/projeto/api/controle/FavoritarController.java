package br.com.projeto.api.controle;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.infra.exception.ValidacaoExisteException;
import br.com.projeto.api.modelo.interacoes.favoritar.*;
import br.com.projeto.api.servico.FavoritarServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritar")
public class FavoritarController {

    @Autowired
    private FavoritarServico favoritarServico;

    @PostMapping
    @Operation(summary = "Favoritar filme", description = "Usuário favorita um filme")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> favoritar(@RequestBody @Valid DTOFavoritar dados) {
        try {
            favoritarServico.favoritar(dados);
            return ResponseEntity.status(201).body("Filme favoritado com sucesso!");
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (ValidacaoExisteException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor: " + e.getMessage());
        }
    }

    @PutMapping
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Adicionar informações adicionais sobre o filme favorito", description = "Usuário adiciona uma nota e um comentário a esse filme favoritado")
    public ResponseEntity<?> favoritarAdicionar(@RequestBody @Valid DTOFavoritarAdicionar dados){
        try{
            favoritarServico.favoritarAdicionar(dados);
            return ResponseEntity.status(200).body("As modificações foram salvas com sucesso!");
        }catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(500).body("Erro interno no servidor: "+e.getMessage());
        }
    }


    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Mostra todos os filmes favoritos", description = "Sistema retorna todos os filmes favoritados registrados no sistema")
    public ResponseEntity<?> selecionar(){
        List<DTOFilmeFavorito> filmesFavoritos = favoritarServico.selecionar();
        return ResponseEntity.status(200).body(filmesFavoritos);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Mostra um filme favorito por id", description = "Sistema retorna um filme favoritado específico informado pela url")
    public  ResponseEntity<?> selecionarPorId(@PathVariable @Valid Long id){
        try{
            DTOFilmeFavorito filmeFavorito = favoritarServico.selecionarPeloId(id);
            return ResponseEntity.status(200).body(filmeFavorito);
        }catch (NotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
