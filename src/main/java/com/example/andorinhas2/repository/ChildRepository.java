package com.example.andorinhas2.repository;

import com.example.andorinhas2.model.ChildTable;
import org.apache.poi.poifs.property.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<ChildTable, Long> {
    List<ChildTable> findByAtiva(Boolean ativa);

}
