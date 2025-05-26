package com.example.andorinhas2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.aspectj.weaver.ast.Not;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ChildDto(Long id,
                       @NotNull
                       @NotBlank
                       String nome,
                       @NotNull
                       LocalDate dataNascimento,
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
