package com.example.andorinhas2.repository;

import com.example.andorinhas2.model.IncomesPayedTable;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IncomePayedRespository extends JpaRepository<IncomesPayedTable, Long> {

    List<IncomesPayedTable> findAllByCriancaId(Long criancaId);

    @Query("SELECT i FROM IncomesPayedTable i WHERE FUNCTION('MONTH', i.dateCreation) = :month AND FUNCTION('YEAR', i.dateCreation) = :year")
    List<IncomesPayedTable> findByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT COALESCE(SUM(i.value), 0) FROM IncomesPayedTable i WHERE i.datePayment BETWEEN :startDate AND :endDate")
    Long sumValueByDatePaymentBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
