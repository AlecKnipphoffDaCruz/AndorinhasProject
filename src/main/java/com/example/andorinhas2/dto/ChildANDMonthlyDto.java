package com.example.andorinhas2.dto;

import com.example.andorinhas2.model.ChildTable;
import com.example.andorinhas2.model.MonthlyTable;

public record ChildANDMonthlyDto(
        ChildTable crianca,
        MonthlyTable mensalidade
) {

}
