package br.com.projeto.api.modelo.interacoes.favoritar;

import jakarta.validation.constraints.NotNull;

public record DTOFavoritarSelecionarId(@NotNull Long idFilmeFavorito) {
}
