package com.example.andorinhas2.repository;

import com.example.andorinhas2.model.ChildTable;
import com.example.andorinhas2.model.ERegistration;
import com.example.andorinhas2.model.RegistrationTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<RegistrationTable, Long> {
    List<RegistrationTable> findByCriancaNome(String nome);
    List<RegistrationTable> findByTipo(ERegistration tipo);
    Optional<RegistrationTable> findTopByCriancaOrderByDatahoraDesc(ChildTable crianca);
    List<RegistrationTable> findByDatahoraBetweenAndTipo(LocalDateTime inicio, LocalDateTime fim, ERegistration entrada);
}
