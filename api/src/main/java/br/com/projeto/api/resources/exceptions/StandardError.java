package br.com.projeto.api.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class StandardError {
    private LocalDateTime timestamp;
    Integer status;
    String error;

    public static StandardError NotFound(){
        return StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(404)
                .error("Filme não encontrado")
                .build();
    }

    public static StandardError BadRequest(){
        return StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(400)
                .error("Título não pode ser vazio")
                .build();
    }

    public static StandardError Conflict(){
        return StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(409)
                .error("Filme já cadastrado")
                .build();
    }
}
