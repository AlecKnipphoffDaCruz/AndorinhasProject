package com.example.andorinhas2.dto;

import com.example.andorinhas2.model.Erole;


import java.time.LocalDateTime;

public record UserDtoUpload(
        Long id,
        String nome,
        String email,
        String senha, Erole role,
        LocalDateTime dataAdmissao, String userImg
) {
}
