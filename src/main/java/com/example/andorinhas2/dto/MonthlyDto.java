package com.example.andorinhas2.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record MonthlyDto(
        @NotNull
        Long crianca_id,
        @NotNull
        Long valor
) {
}
