package br.com.projeto.api.controle;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.infra.exception.ValidacaoCampoObrigatorioException;
import br.com.projeto.api.infra.exception.ValidacaoExisteException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

import java.util.List;


@RestController
public class FilmeControle {

    @Autowired
    private FilmeServico filmeServico;

    @PostMapping("/api")
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Salvar filme", description = "Usuário passa as informações necessárias para a criação do filme no sistema")
    public ResponseEntity<String> cadastrar(@RequestBody Filme obj){
        try{
            this.filmeServico.cadastrar(obj);
            return ResponseEntity.status(HttpStatus.CREATED).body("Filme cadastrado com sucesso!");
        }catch (ValidacaoExisteException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ValidacaoCampoObrigatorioException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar filme!");
        }
    }

    @GetMapping("/api")
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Mostra todos os filme", description = "Sistema retorna todos os filmes cadastrados")
    public ResponseEntity<?> selecionar() {
        List<Filme> filmes = filmeServico.selecionar();
        return ResponseEntity.status(200).body(filmes);
    }

    @GetMapping("")
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Mostra Hello World na tela")
    public String mensagem(){
        return "Hello World";
    }

    @GetMapping("/api/{id}")
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Mostra filme pelo id", description = "Usuário envia id de um filme pela url e sistema retorna o filme")
    public ResponseEntity<?> selecionarPeloId(@PathVariable Long id) {

        try{
            var filme = filmeServico.selecionarPeloId(id);
            return ResponseEntity.status(200).body(filme);
        }catch (NotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/api")
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Editar informações de um filme", description = "Usuário passa todos os dados novos do filme e sistema salva essa alteração")
    public ResponseEntity<?> editar(@RequestBody Filme obj) {

        try{
            filmeServico.editar(obj);
            return ResponseEntity.status(HttpStatus.OK).body("Filme editado com sucesso!");
        }catch (ValidacaoCampoObrigatorioException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/api/{id}")
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Remove filme", description = "Sistema usa id informado na url para remover filme do sistema")
    public ResponseEntity<?> remover(@PathVariable Long id){
        try{
            filmeServico.remover(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT  ).body("Filme removido com sucesso!");
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/api/maiorNota")
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Mostra filme com a maior nota")
    public ResponseEntity<?> maiorNota() {
        var filmeMaiorNota = filmeServico.mostraMaiorNota();
        return ResponseEntity.status(200).body(filmeMaiorNota);
    }

    @GetMapping("/api/popularidadeMaiorQue/{n}")
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Mostra filmes com popularidade maior que n", description = "Recebe um número n e usa ele para fazer uma busca no banco de dados de filmes que possuem uma popularidade maior que n")
    public ResponseEntity<?> popularidadeMaiorQue(@PathVariable float n) {
        var filmesPopularidade = filmeServico.mostraPopularidadeMaiorQue(n);
        return ResponseEntity.status(200).body(filmesPopularidade);
    }

    @GetMapping("/status")
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Retorna o status 201 CREATED")
    public ResponseEntity<?> status() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
