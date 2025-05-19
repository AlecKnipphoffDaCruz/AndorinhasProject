package com.example.andorinhas2.dto;

import com.example.andorinhas2.model.ETypeSpent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SpentDto(
        @NotNull @NotBlank
        String descricao,
        @NotNull
        Long valor,
        @NotNull
        @NotBlank
        ETypeSpent tipo
) {}
