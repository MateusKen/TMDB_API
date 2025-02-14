package br.com.projeto.api.servico;

import br.com.projeto.api.resources.exceptions.StandardError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.projeto.api.modelo.Filme;
import br.com.projeto.api.modelo.Mensagem;
import br.com.projeto.api.repositorio.Repositorio;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class Servico{
    
    @Autowired
    private Mensagem mensagem;

    @Autowired
    private Repositorio acao;

    public ResponseEntity<?> cadastrar(Filme obj){
        Optional<Filme> obj2 = Optional.ofNullable(obj);
        if(obj.getTitle().isEmpty()){
            StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Título não pode ser vazio");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }else if(obj2 != null){
            StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.CONFLICT.value(), "Filme já cadastrado");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } else{
            return new ResponseEntity<>(acao.save(obj), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> selecionar(){
        return new ResponseEntity<>(acao.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> selecionarPeloId(int id){
        Optional<Filme> obj = Optional.ofNullable(acao.findById(id));
        if(obj.isEmpty()){
            StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Filme não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }else{
            return new ResponseEntity<>(obj.get(), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> editar(Filme obj){
        Optional<Filme> obj2 = Optional.ofNullable(obj);
        if(acao.countById(obj.getId()) == 0){
            StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Filme não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }else if(obj.getTitle().isEmpty()){
            StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Título não pode ser vazio");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }else if(obj2 != null){
            StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.CONFLICT.value(), "Filme já cadastrado");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }else{
            return new ResponseEntity<>(acao.save(obj), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> remover(int id){

        if(acao.countById(id) == 0){
            StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Filme não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }else{
            Filme obj = acao.findById(id);
            acao.delete(obj);
            mensagem.setMensagem("Registro removido com sucesso");
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> mostraMaiorNota(){
        return new ResponseEntity<>(acao.maiorNota(), HttpStatus.OK);
    }

    public ResponseEntity<?> mostraPopularidadeMaiorQue(float n){
        return new ResponseEntity<>(acao.popularidadeMaiorQue(n), HttpStatus.OK);
    }

}