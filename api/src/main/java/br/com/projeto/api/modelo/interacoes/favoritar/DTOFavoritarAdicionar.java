package br.com.projeto.api.modelo.interacoes.favoritar;

import jakarta.validation.constraints.NotNull;

public record DTOFavoritarAdicionar(@NotNull Long idFavorito, String comment, Integer rating) {
}
