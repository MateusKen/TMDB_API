package br.com.projeto.api.modelo.interacoes.favoritar;

import br.com.projeto.api.modelo.filme.Filme;
import br.com.projeto.api.modelo.usuario.DTOUsuario;
import br.com.projeto.api.modelo.usuario.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Chave estrangeira para Usuario
    @Schema(description = "Usuário que vai favoritar filme")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false) // Chave estrangeira para Filme
    @Schema(description = "Filme favoritado")
    private Filme filme;

    @Schema(description = "Nota do usuário assosciada ao filme", example = "7")
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Comentário do usuário assosciado ao filme", example = "Filme muito bom")
    private String comment;

}