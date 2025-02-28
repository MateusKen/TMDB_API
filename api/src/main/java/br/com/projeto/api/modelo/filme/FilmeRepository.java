package br.com.projeto.api.modelo.filme;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
    
    List<Filme> findAll();

    Filme findById(int id);

    Filme findByTitle(String title);

    @Query(value="SELECT * FROM filmes WHERE vote_average = (select max(vote_average) from filmes)", nativeQuery = true)
    Filme maiorNota();

    @Query(value="SELECT * FROM filmes WHERE popularity>:n", nativeQuery = true)
    List<Filme> popularidadeMaiorQue(float n);

    int countById(Long id);
}
