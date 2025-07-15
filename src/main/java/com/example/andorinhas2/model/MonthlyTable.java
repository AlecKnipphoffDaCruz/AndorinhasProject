package com.example.andorinhas2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDate;

@Entity
@Table(name = "mensalidade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "crianca_id", referencedColumnName = "id", unique = true)
    @NotNull
    private ChildTable crianca;

    @NotNull
    private Long valor;

    @NotNull
    @Column(name = "vencimento")
    private LocalDate dataVencimento;

    @NotNull
    @Column(name = "paga")
    private Boolean estaPaga;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "data-criacao")
    private LocalDate dataCriacao;

    public boolean isPaga() {
        if (getEstaPaga().equals(true)){
            return true;
        }else {
            return false;
        }
    }

}
