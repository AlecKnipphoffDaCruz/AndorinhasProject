package com.example.andorinhas2.dto;

import com.example.andorinhas2.model.ERegistration;
import com.example.andorinhas2.model.Erole;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.aspectj.weaver.ast.Not;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
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
                       String telefonePai,
                       ERegistration eRegistration,
                       Boolean ativa
        ) {
}
