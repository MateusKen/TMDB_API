package br.com.projeto.api.controle;

import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.api.modelo.Filme;
import br.com.projeto.api.servico.Servico;

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
public class Controle {

    @Autowired
    private Servico servico;

    @PostMapping("/api")
    public ResponseEntity<?> cadastrar(@RequestBody Filme obj){
        return servico.cadastrar(obj);
    }

    @GetMapping("/api")
    public ResponseEntity<?> selecionar() {
        return servico.selecionar();
    }

    @GetMapping("")
    public String mensagem(){
        return "Hello World 1";
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<?> selecionarPeloId(@PathVariable int id) {
        return servico.selecionarPeloId(id);
    }

    @PutMapping("/api")
    public ResponseEntity<?> editar(@RequestBody Filme obj) {
        return servico.editar(obj);
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<?> remover(@PathVariable int id){
        return servico.remover(id);
    }

    @GetMapping("/api/maiorNota")
    public ResponseEntity<?> maiorNota() {
        return servico.mostraMaiorNota();
    }

    @GetMapping("/api/popularidadeMaiorQue/{n}")
    public ResponseEntity<?> popularidadeMaiorQue(@PathVariable float n) {
        return servico.mostraPopularidadeMaiorQue(n);
    }

    @GetMapping("/status")
    public ResponseEntity<?> status() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
