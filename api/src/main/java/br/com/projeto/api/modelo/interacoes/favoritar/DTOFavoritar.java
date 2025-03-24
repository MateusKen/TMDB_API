package br.com.projeto.api.modelo.interacoes.favoritar;

import jakarta.validation.constraints.NotNull;

public record DTOFavoritar (@NotNull Long idUsuario, @NotNull Long idFilme) {
}
