package br.com.projeto.api.modelo.filme;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@ToString
@Table(name = "filmes")
@Builder
public class Filme {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;

    @NotBlank(message = "Título não pode ser vazio")
    @NotNull
    @Schema(description = "Título do filme", example = "Clube da luta")
    private String title;

    @Schema(description = "Sinopse do filme", example = "É um filme que em que se luta e não se fala sobre clube da luta")
    private String overview;

    @Schema(description = "Data de lançamento", example = "10-29-1999")
    private String release_date;

    @Schema(description = "Indicador de popularidade do filme", example = "14.4")
    private float popularity;

    @Schema(description = "Média de votos do filme", example = "6.89")
    private float vote_average;

    @Schema(description = "Número de votos do filme", example = "1231350")
    private int vote_count;

}
