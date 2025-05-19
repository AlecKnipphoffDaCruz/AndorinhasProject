package com.example.andorinhas2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChildDto(Long id,
                       @NotNull
                       @NotBlank
                       String nome,
                       @NotNull
                       Long idade,
                       @NotNull
                       Long turma) {
}
