package com.example.andorinhas2.dto;

import com.example.andorinhas2.model.Erole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UserDto(
        Long id,
        @NotNull
        @NotBlank
        String nome,
        @NotNull
        @NotBlank
        String email,
        @NotNull
        @NotBlank
        String senha,
        @NotNull
        Erole role,
        LocalDateTime dataAdmissao, String userImg) {
}

