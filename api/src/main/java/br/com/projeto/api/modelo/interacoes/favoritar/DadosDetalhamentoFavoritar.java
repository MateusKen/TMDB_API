package br.com.projeto.api.modelo.interacoes.favoritar;

import org.jetbrains.annotations.NotNull;

public record DadosDetalhamentoFavoritar(@NotNull Long idUsuario, @NotNull Long idFilme) {
}
