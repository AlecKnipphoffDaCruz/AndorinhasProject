package com.example.andorinhas2.model;

import com.example.andorinhas2.dto.ChildDto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "crianca", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome", "idade"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChildTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "O Nome Completo é obrigatório")
    @NotBlank
    @Column(unique = true)
    private String nome;
    @NotNull
    private LocalDate dataNascimento;
    private Long turma;
    @NotNull(message = "Não está chegando o id!")
    private Integer avatarId;
    @Column(name = "nome_pai")
    @NotNull
    @NotBlank
    private String nomePai;
    @Column(name = "telefone_pai")
    @NotNull
    private String telefonePai;
    @Enumerated(EnumType.STRING)
    @Column(name = "eregistration")
    private ERegistration eRegistration;



    public ChildTable(@Valid ChildDto childDto) {
        this.id = childDto.id();
        this.nome = childDto.nome();
        this.dataNascimento = childDto.dataNascimento();
        this.turma = childDto.turma();
        this.avatarId = childDto.avatarId();
        this.nomePai = childDto.nomePai();
        this.telefonePai = childDto.telefonePai();
    }
}
