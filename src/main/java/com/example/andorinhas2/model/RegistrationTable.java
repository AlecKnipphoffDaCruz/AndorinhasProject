package com.example.andorinhas2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "registro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "crianca_id", referencedColumnName = "id", unique = true)
    @NotNull
    private ChildTable crianca;


    @Column(name = "data_hora", nullable = false)
    @NotNull
    private LocalDateTime datahora;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ERegistration tipo;

    public RegistrationTable(ChildTable crianca, ERegistration tipo, LocalDateTime now) {
        this.crianca = crianca;
        this.tipo = tipo;
        this.datahora = now;
    }
}
