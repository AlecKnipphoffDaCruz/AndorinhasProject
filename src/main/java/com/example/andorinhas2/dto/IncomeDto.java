package com.example.andorinhas2.dto;

import com.example.andorinhas2.model.EPayType;

import java.time.LocalDate;

public record IncomeDto(
        Long id,
        Long criancaId,
        Long valor,
        Integer dias,
        LocalDate dataCriacao,
        LocalDate dataPagamento,
        Boolean paga,
        EPayType tipoPagamento
) {
}
