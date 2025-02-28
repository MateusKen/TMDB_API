package br.com.projeto.api.controle;

import br.com.projeto.api.modelo.interacoes.favoritar.DadosFavoritarFilme;
import br.com.projeto.api.modelo.interacoes.favoritar.DadosTransferenciaFavoritar;
import br.com.projeto.api.servico.FavoritarServico;
import br.com.projeto.api.servico.FilmeServico;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favoritar")
public class FavoritarController {

    @Autowired
    private FavoritarServico favoritarServico;
    @Autowired
    private FilmeServico filmeServico;

    @PostMapping
    @Transactional
    public ResponseEntity favoritar(@RequestBody @Valid DadosFavoritarFilme dados){
        return favoritarServico.favoritar(dados.idUsuario(), dados.idFilme());
    }

    @PutMapping
    public ResponseEntity favoritarAdicionar(@RequestBody @Valid DadosTransferenciaFavoritar dados){
        return favoritarServico.favoritarAdicionar(dados.idFilmeFavorito(), dados.rating(), dados.comment());
    }

    @GetMapping
    public ResponseEntity selecionar(){
        return favoritarServico.selecionar();
    }

    @GetMapping("/{id}")
    public  ResponseEntity selecionarPorId(@PathVariable @Valid Long id){
        return favoritarServico.selecionarPeloId(id);
    }
}
