package br.com.projeto.api.servico;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.projeto.api.modelo.Filme;
import br.com.projeto.api.modelo.Mensagem;
import br.com.projeto.api.repositorio.Repositorio;

import java.util.Optional;
import java.util.List;

@Service
public class Servico implements Repositorio{
    
    @Autowired
    private Mensagem mensagem;

    @Autowired
    private Repositorio acao;

//    public ResponseEntity<?> cadastrar(Filme obj){
//
//        if(obj.getTitle().isEmpty()){
//            mensagem.setMensagem("Titulo não pode ser vazio");
//            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
//        }else{
//            return new ResponseEntity<>(acao.save(obj), HttpStatus.CREATED);
//        }
//    }

//    public ResponseEntity<?> selecionar(){
//        return new ResponseEntity<>(acao.findAll(), HttpStatus.OK);
//    }

    @Override
    public List<Filme> findAll() {
        return List.of();
    }

    @Override
    public Filme findById(int id){

        Optional<Filme> obj = Optional.ofNullable(acao.findById(id));
        return obj.orElseThrow(() -> new ObjectNotFoundException("Filme não encontrado"));
    }

    @Override
    public void flush() {
        
    }

//    public ResponseEntity<?> editar(Filme obj){
//
//        if(acao.countById(obj.getId()) == 0){
//            mensagem.setMensagem("Código não encontrado");
//            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
//        }else if(obj.getTitle().isEmpty()){
//            mensagem.setMensagem("Nome não pode ser vazio");
//            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
//        }else{
//            return new ResponseEntity<>(acao.save(obj), HttpStatus.OK);
//        }
//    }
//
//    public ResponseEntity<?> remover(int id){
//
//        if(acao.countById(id) == 0){
//            mensagem.setMensagem("Código não encontrado");
//            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
//        }else{
//            Filme obj = acao.findById(id);
//            acao.delete(obj);
//            mensagem.setMensagem("Registro removido com sucesso");
//            return new ResponseEntity<>(mensagem, HttpStatus.OK);
//        }
//    }
//
//    public ResponseEntity<?> mostraMaiorNota(){
//        return new ResponseEntity<>(acao.maiorNota(), HttpStatus.OK);
//    }
//
//    public ResponseEntity<?> mostraPopularidadeMaiorQue(float n){
//        return new ResponseEntity<>(acao.popularidadeMaiorQue(n), HttpStatus.OK);
//    }

//    @Override
//    public Filme findById(Integer id){
//        Optional<Filme> obj =  acao.findById(id);
//        return obj.orElseThrow(() -> new ObjectNotFoundException("Filme não encontrado"));
//    }
}