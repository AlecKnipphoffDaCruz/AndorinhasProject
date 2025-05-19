package com.example.andorinhas2.repository;

import com.example.andorinhas2.model.SpentTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SpentRepository extends JpaRepository<SpentTable, Long> {
    void deleteById(Long id);

    @Query(
            value = "SELECT * FROM despesa " +
                    "WHERE EXTRACT(MONTH FROM data) = :mes " +
                    "AND EXTRACT(YEAR FROM data) = :ano",
            nativeQuery = true
    )
    List<SpentTable> findByMesEAno(@Param("mes") int mes, @Param("ano") int ano);
}
