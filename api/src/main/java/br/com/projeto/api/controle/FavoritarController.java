package br.com.projeto.api.controle;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.infra.exception.ValidacaoExisteException;
import br.com.projeto.api.modelo.interacoes.favoritar.*;
import br.com.projeto.api.servico.FavoritarServico;
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
    @Transactional
    public ResponseEntity favoritar(@RequestBody @Valid DTOFavoritar dados){
        try{
            favoritarServico.favoritar(dados);
            return ResponseEntity.status(201).body("Filme favoritado com sucesso!");
        }catch (NotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }catch (ValidacaoExisteException e){
            return ResponseEntity.status(409).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(500).body("Erro interno no servidor: "+e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity favoritarAdicionar(@RequestBody @Valid DTOFavoritarAdicionar dados){
        try{
            favoritarServico.favoritarAdicionar(dados);
            return ResponseEntity.status(200).body("Filme adicionado com sucesso!");
        }catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(500).body("Erro interno no servidor: "+e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity selecionar(){
        List<DTOFilmeFavorito> filmesFavoritos = favoritarServico.selecionar();
        return ResponseEntity.status(200).body(filmesFavoritos);
    }

    @GetMapping("/{id}")
    public  ResponseEntity selecionarPorId(@PathVariable @Valid Long id){
        try{
            DTOFilmeFavorito filmeFavorito = favoritarServico.selecionarPeloId(id);
            return ResponseEntity.status(200).body(filmeFavorito);
        }catch (NotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
