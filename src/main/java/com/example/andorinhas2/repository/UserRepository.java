package com.example.andorinhas2.repository;

import com.example.andorinhas2.model.UserTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserTable, Long> {
    @Transactional
    void deleteByUsuarioId(Long usuarioId);
    UserDetails findByEmail (String email);

    UserTable findByUsuarioId (Long usuarioId);
    boolean existsByEmail(String email);
    boolean existsByNome(String nome);
}
