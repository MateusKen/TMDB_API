package br.com.projeto.api.modelo.watchlist;

import org.jetbrains.annotations.NotNull;

public record DadosFavoritarFilme(@NotNull Long idUsuario, Long idFilme) {
}
