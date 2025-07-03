package com.example.andorinhas2.dto;

import com.example.andorinhas2.model.Erole;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UserDto(
        Long id,
        @NotNull String nome,
        String email, String senha, Erole role,
        LocalDateTime dataAdmissao, String userImg) {


}
