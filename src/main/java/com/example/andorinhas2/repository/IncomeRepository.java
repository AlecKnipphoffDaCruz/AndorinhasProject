package com.example.andorinhas2.repository;

import com.example.andorinhas2.model.IncomeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncomeRepository extends JpaRepository<IncomeTable, Long> {
    @Query("SELECT i FROM IncomeTable i WHERE FUNCTION('MONTH', i.dateCreation) = :month AND FUNCTION('YEAR', i.dateCreation) = :year")
    List<IncomeTable> findByMonthAndYear(@Param("month") int month, @Param("year") int year);

}
