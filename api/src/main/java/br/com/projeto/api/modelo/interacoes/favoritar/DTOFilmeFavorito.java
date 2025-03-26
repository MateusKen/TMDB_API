package br.com.projeto.api.modelo.interacoes.favoritar;

import br.com.projeto.api.modelo.filme.Filme;
import br.com.projeto.api.modelo.usuario.DTOUsuario;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOFilmeFavorito{
    private Long id;
    private Filme filme;
    private DTOUsuario usuario;
    private Integer rating;
    private String comment;
}
