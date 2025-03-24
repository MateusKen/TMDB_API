package br.com.projeto.api.servico;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.infra.exception.ValidacaoCampoObrigatorioException;
import br.com.projeto.api.infra.exception.ValidacaoExisteException;
import br.com.projeto.api.modelo.filme.FilmeRepository;
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

    public void cadastrar(Filme obj)  {
        Optional<Filme> obj2 = Optional.ofNullable(acao.findByTitle(obj.getTitle()));
        if(obj.getTitle().isEmpty()){
            throw new ValidacaoCampoObrigatorioException("O campo título é obrigatório!");
        }else if(obj2.isPresent()){
            throw new ValidacaoExisteException("Filme já cadastrado!");
        } else{
            acao.save(obj);
        }
    }

    public ResponseEntity<?> selecionar(){
        return new ResponseEntity<>(acao.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> selecionarPeloId(Long id){
        Optional<Filme> obj = acao.findById(id);
        if(obj.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(obj.get(), HttpStatus.OK);
        }
    }

    public void editar(Filme obj){
        if(acao.countById(obj.getId()) == 0){
            throw new NotFoundException("Filme não encontrado!");
        }else if(obj.getTitle().isEmpty()){
            throw new ValidacaoCampoObrigatorioException("O campo título é obrigatório!");
        }else{
            acao.save(obj);
        }
    }

    public void remover(Long id){
        Optional<Filme> obj = acao.findById(id);
        if(obj.isPresent()){
            acao.deleteById(id);
        }
        throw new NotFoundException("Filme não encontrado!");
    }

    public ResponseEntity<?> mostraMaiorNota(){
        return new ResponseEntity<>(acao.maiorNota(), HttpStatus.OK);
    }

    public ResponseEntity<?> mostraPopularidadeMaiorQue(float n){
        return new ResponseEntity<>(acao.popularidadeMaiorQue(n), HttpStatus.OK);
    }

}