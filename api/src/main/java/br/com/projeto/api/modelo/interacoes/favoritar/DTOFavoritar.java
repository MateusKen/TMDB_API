package br.com.projeto.api.modelo.interacoes.favoritar;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record DTOFavoritar (@NotNull @Schema(description = "Id do usuário", example = "1")
                            Long idUsuario,
                            @NotNull @Schema(description = "Id do filme", example = "1")
                            Long idFilme) {
}
