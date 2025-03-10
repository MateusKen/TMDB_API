package br.com.projeto.api.servico;

import br.com.projeto.api.modelo.filme.FilmeRepository;
import br.com.projeto.api.resources.exceptions.StandardError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.projeto.api.modelo.filme.Filme;

import java.util.Optional;

@Service
public class FilmeServico {

    @Autowired
    private FilmeRepository acao;

    public ResponseEntity<?> cadastrar(Filme obj){
        Optional<Filme> obj2 = Optional.ofNullable(acao.findByTitle(obj.getTitle()));

        if(obj.getTitle().isEmpty()){
            StandardError error = StandardError.BadRequest();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }else if(obj2.isPresent()){
            StandardError error = StandardError.Conflict();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } else{
            return new ResponseEntity<>(acao.save(obj), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> selecionar(){
        return new ResponseEntity<>(acao.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> selecionarPeloId(Long id){
        Optional<Filme> obj = acao.findById(id);
        if(obj.isEmpty()){
            StandardError error = StandardError.NotFound();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }else{
            return new ResponseEntity<>(obj.get(), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> editar(Filme obj){
        if(acao.countById(obj.getId()) == 0){
            StandardError error = StandardError.NotFound();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }else if(obj.getTitle().isEmpty()){
            StandardError error = StandardError.BadRequest();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }else{
            return new ResponseEntity<>(acao.save(obj), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> remover(Long id){
        Optional<Filme> obj = acao.findById(id);
        if(obj.isPresent()){
            acao.deleteById(id);
            return new ResponseEntity<>("Filme removido com sucesso!", HttpStatus.NO_CONTENT);
        }
        StandardError error = StandardError.NotFound();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    public ResponseEntity<?> mostraMaiorNota(){
        return new ResponseEntity<>(acao.maiorNota(), HttpStatus.OK);
    }

    public ResponseEntity<?> mostraPopularidadeMaiorQue(float n){
        return new ResponseEntity<>(acao.popularidadeMaiorQue(n), HttpStatus.OK);
    }

}