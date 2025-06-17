package com.example.andorinhas2.model;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SemanaModel {
    Integer segunda;
    Integer terca;
    Integer quarta;
    Integer quinta;
    Integer sexta;
    Integer sabado;
    Integer domingo;
}
