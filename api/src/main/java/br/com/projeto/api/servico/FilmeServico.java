package br.com.projeto.api.servico;

import br.com.projeto.api.infra.exception.NotFoundException;
import br.com.projeto.api.infra.exception.ValidacaoCampoObrigatorioException;
import br.com.projeto.api.modelo.filme.FilmeRepository;
import br.com.projeto.api.validacao.filme.ValidacaoFilme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.projeto.api.modelo.filme.Filme;

import java.util.List;
import java.util.Optional;

@Service
public class FilmeServico {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private List<ValidacaoFilme<?>> validacoes;

    public void cadastrar(Filme obj)  {

        /*
        * nas validacoes existem duas validacaoes 1 para verificar se titulo é vazio
        * e outra para ver se o obj ja existe
        * */
        validacoes.forEach(v -> {
            if(v.getTipo().isInstance(obj)){
                ValidacaoFilme<Filme> ValidacaoFilme = (ValidacaoFilme<Filme>) v;
                ValidacaoFilme.validar(obj);
            }
        });

        filmeRepository.save(obj);
    }

    public List<Filme> selecionar(){
        List<Filme> filmes = filmeRepository.findAll();

        return filmes.stream()
                .map(filme -> new Filme(
                        filme.getId(),
                        filme.getTitle(),
                        filme.getOverview(),
                        filme.getRelease_date(),
                        filme.getPopularity(),
                        filme.getVote_average(),
                        filme.getVote_count()
                ))
                .toList();
    }

    public Filme selecionarPeloId(Long id){

        validacoes.forEach(v -> {
            if(v.getTipo().isInstance(id)){
                ValidacaoFilme<Long> ValidacaoFilme = (ValidacaoFilme<Long>) v;
                ValidacaoFilme.validar(id);
            }
        });

        return filmeRepository.findById(id).get();
    }

    public void editar(Filme obj){
        validacoes.forEach(v -> {
            if(v.getTipo().isInstance(obj)){
                ValidacaoFilme<Filme> ValidacaoFilme = (ValidacaoFilme<Filme>) v;
                ValidacaoFilme.validar(obj);
            }
        });
        filmeRepository.save(obj);
    }

    public void remover(Long id){

        validacoes.forEach(v -> {
            if(v.getTipo().isInstance(id)){
                ValidacaoFilme<Long> ValidacaoFilme = (ValidacaoFilme<Long>) v;
                ValidacaoFilme.validar(id);
            }
        });

        filmeRepository.deleteById(id);
    }

    public Filme mostraMaiorNota(){
        return filmeRepository.maiorNota();
    }

    public List<Filme> mostraPopularidadeMaiorQue(float n){
        return filmeRepository.popularidadeMaiorQue(n);
    }

}