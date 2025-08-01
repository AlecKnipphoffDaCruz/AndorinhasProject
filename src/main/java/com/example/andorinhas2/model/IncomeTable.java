package com.example.andorinhas2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "incomes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crianca_id", insertable = false, updatable = false)
    private ChildTable crianca;

    @NotNull
    @Column(name = "valor")
    private Long value;

    @Column(name = "dias")
    private Integer days;

    @NotNull
    @Column(name = "data_criacao")
    private LocalDate dateCreation;

    @NotNull
    @Column(name = "data_pagamento")
    private LocalDate datePayment;

    @NotNull
    @Column(name = "paga")
    private Boolean isPaga;

    @Column(name = "tipo_pagamento")
    @Enumerated(EnumType.STRING)
    private EPayType paymentType;


}
