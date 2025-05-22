package com.example.andorinhas2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.aspectj.weaver.ast.Not;

public record ChildDto(Long id,
                       @NotNull
                       @NotBlank
                       String nome,
                       @NotNull
                       Long idade,
                       @NotNull
                       Long turma,
                       @NotNull
                       Integer avatarId,
                       @NotNull
                       String nomePai,
                       @NotNull
                       String telefonePai
        ) {
}
