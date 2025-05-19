package com.example.andorinhas2.repository;

import com.example.andorinhas2.model.ChildTable;
import com.example.andorinhas2.model.MonthlyTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MonthlyRepository extends JpaRepository<MonthlyTable, Long> {
    List<MonthlyTable> findByCriancaId(Long criancaId);


    @Query(
            value = "SELECT * FROM mensalidade " +
                    "WHERE EXTRACT(MONTH FROM vencimento) = :mes " +
                    "AND EXTRACT(YEAR FROM vencimento) = :ano",
            nativeQuery = true
    )
    List<MonthlyTable> findByMesEAno(@Param("mes") int mes, @Param("ano") int ano);
    @Query(
            value = "SELECT * FROM mensalidade " +
                    "WHERE EXTRACT(MONTH FROM data_pagamento) = :mes " +
                    "AND EXTRACT(YEAR FROM data_pagamento) = :ano " +
                    "AND paga = true",
            nativeQuery = true
    )
    List<MonthlyTable> findByMesEAnoAndPaga(@Param("mes") int mes, @Param("ano") int ano);


    boolean existsByCriancaAndDataVencimento(ChildTable criancaId, LocalDate data);

    Optional<MonthlyTable> findFirstByCriancaIdAndEstaPagaFalseOrderByDataVencimentoAsc(Long criancaId);

    MonthlyTable findTopByCriancaIdOrderByDataVencimentoDesc(Long criancaId);

    Long countByDataPagamentoIsNull();
}
