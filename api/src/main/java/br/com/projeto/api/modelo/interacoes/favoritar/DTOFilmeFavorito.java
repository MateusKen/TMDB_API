package br.com.projeto.api.modelo.interacoes.favoritar;

import br.com.projeto.api.modelo.filme.Filme;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOFilmeFavorito{
    private Long id;
    private Filme filme;
    private Long idUsuario;
    private String loginUsuario;
    private Integer rating;
    private String comment;
}
