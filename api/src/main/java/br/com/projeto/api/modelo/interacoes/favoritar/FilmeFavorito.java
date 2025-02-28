package br.com.projeto.api.modelo.interacoes.favoritar;

import br.com.projeto.api.modelo.filme.Filme;
import br.com.projeto.api.modelo.usuario.DTOUsuario;
import br.com.projeto.api.modelo.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorite_movies")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilmeFavorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Chave estrangeira para Usuario
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false) // Chave estrangeira para Filme
    private Filme filme;

    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

}