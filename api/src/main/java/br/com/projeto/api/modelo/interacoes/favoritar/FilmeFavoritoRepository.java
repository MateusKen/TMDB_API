package br.com.projeto.api.modelo.interacoes.favoritar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FilmeFavoritoRepository extends JpaRepository<FilmeFavorito, Long> {
    Optional<FilmeFavorito> findById(Long id);
    List<FilmeFavorito> findAll();

    boolean existsByUsuarioIdAndFilmeId(Long usuarioId, Long filmeId);
}
