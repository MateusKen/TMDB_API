package br.com.projeto.api.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.projeto.api.modelo.Filme;

@Repository
public interface Repositorio extends JpaRepository<Filme, Integer> {
    
    List<Filme> findAll();

    Filme findById(int id);

//    @Query(value="SELECT * FROM filmes WHERE vote_average = (select max(vote_average) from filmes)", nativeQuery = true)
//    Filme maiorNota();
//
//    @Query(value="SELECT * FROM filmes WHERE popularity>:n", nativeQuery = true)
//    List<Filme> popularidadeMaiorQue(float n);
//
//    int countById(int id);

}
