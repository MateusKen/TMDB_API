package br.com.projeto.api.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
@Getter @Setter
public class DTOFilme {
    private String title;
    private String overview;
    private String release_date;
    private float popularity;
    private float vote_average;
    private int vote_count;

    public DTOFilme converte(@NotNull Filme filme){
        DTOFilme dto = new DTOFilme();
        dto.setTitle(filme.getTitle());
        dto.setOverview(filme.getOverview());
        dto.setRelease_date(filme.getRelease_date());
        dto.setPopularity(filme.getPopularity());
        dto.setVote_average(filme.getVote_average());
        dto.setVote_count(filme.getVote_count());
        return dto;
    }
}
