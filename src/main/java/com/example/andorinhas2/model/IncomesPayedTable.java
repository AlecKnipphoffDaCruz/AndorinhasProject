package com.example.andorinhas2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "incomes_pagos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomesPayedTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crianca_id", insertable = false, updatable = false)
    private ChildTable crianca;

    @Column(name = "valor")
    private Long value;

    @Column(name = "dias")
    private Integer days;

    @Column(name = "data_criacao")
    private LocalDate dateCreation;

    @Column(name = "data_pagamento")
    private LocalDate datePayment;

    @Column(name = "paga")
    private Boolean isPaga;

    @Column(name = "tipo_pagamento")
    @Enumerated(EnumType.STRING)
    private EPayType paymentType;


}
