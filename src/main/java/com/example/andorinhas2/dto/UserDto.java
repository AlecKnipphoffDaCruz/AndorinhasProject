package com.example.andorinhas2.dto;

import com.example.andorinhas2.model.Erole;
import jakarta.validation.constraints.NotNull;

public record UserDto(
        Long id,
        @NotNull String nome,
        String email, String senha, Erole role) {


}
