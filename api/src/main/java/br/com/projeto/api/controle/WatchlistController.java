package br.com.projeto.api.controle;

import br.com.projeto.api.modelo.watchlist.DadosFavoritarFilme;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("watchlist")
public class WatchlistController {

    @PostMapping
    @Transactional
    public ResponseEntity marcar(@RequestBody @Valid DadosFavoritarFilme dados){
        System.out.println(dados);
        return ResponseEntity.ok(new DadosDetalhadosFilme(null, null, null));
    }
}
