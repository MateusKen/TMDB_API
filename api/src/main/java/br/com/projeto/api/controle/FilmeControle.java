package br.com.projeto.api.controle;

import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.api.modelo.filme.Filme;
import br.com.projeto.api.servico.FilmeServico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class FilmeControle {

    @Autowired
    private FilmeServico filmeServico;

    @PostMapping("/api")
    public ResponseEntity<?> cadastrar(@RequestBody Filme obj){
        return filmeServico.cadastrar(obj);
    }

    @GetMapping("/api")
    public ResponseEntity<?> selecionar() {
        return filmeServico.selecionar();
    }

    @GetMapping("")
    public String mensagem(){
        return "Hello World 1";
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<?> selecionarPeloId(@PathVariable Long id) {
        return filmeServico.selecionarPeloId(id);
    }

    @PutMapping("/api")
    public ResponseEntity<?> editar(@RequestBody Filme obj) {
        return filmeServico.editar(obj);
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id){
        return filmeServico.remover(id);
    }

    @GetMapping("/api/maiorNota")
    public ResponseEntity<?> maiorNota() {
        return filmeServico.mostraMaiorNota();
    }

    @GetMapping("/api/popularidadeMaiorQue/{n}")
    public ResponseEntity<?> popularidadeMaiorQue(@PathVariable float n) {
        return filmeServico.mostraPopularidadeMaiorQue(n);
    }

    @GetMapping("/status")
    public ResponseEntity<?> status() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
