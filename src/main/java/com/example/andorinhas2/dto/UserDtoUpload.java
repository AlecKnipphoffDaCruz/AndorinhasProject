package com.example.andorinhas2.dto;

import com.example.andorinhas2.model.Erole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDateTime;

public record UserDtoUpload(
        Long id,
        String nome,
        String email,
        @NotNull
        @NotBlank
        String senha,
        Erole role,
        LocalDateTime dataAdmissao, String userImg
) {
}
