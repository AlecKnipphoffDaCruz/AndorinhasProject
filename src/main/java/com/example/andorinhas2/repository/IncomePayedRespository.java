package com.example.andorinhas2.repository;

import com.example.andorinhas2.model.IncomesPayedTable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IncomePayedRespository extends JpaRepository<IncomesPayedTable, Long> {

    List<IncomesPayedTable> findAllByCriancaId(Long criancaId);

    @Query("SELECT i FROM IncomesPayedTable i WHERE FUNCTION('MONTH', i.dateCreation) = :month AND FUNCTION('YEAR', i.dateCreation) = :year")
    List<IncomesPayedTable> findByMonthAndYear(@Param("month") int month, @Param("year") int year);

}
