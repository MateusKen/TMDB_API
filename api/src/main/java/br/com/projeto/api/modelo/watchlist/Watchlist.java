package br.com.projeto.api.modelo.watchlist;

import br.com.projeto.api.modelo.filme.Filme;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Watchlist {

    private String nome;
    private List<Filme> lista;

}
