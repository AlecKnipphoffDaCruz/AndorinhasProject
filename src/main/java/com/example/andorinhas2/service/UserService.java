package com.example.andorinhas2.service;

import com.example.andorinhas2.dto.UserDto;
import com.example.andorinhas2.dto.UserDtoUpload;
import com.example.andorinhas2.model.UserTable;
import com.example.andorinhas2.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserTable> listarTodos() {
        return userRepository.findAll();
    }

    @Transactional
    public void cadastrar(UserDto userDto) {

        if (userRepository.existsByEmail(userDto.email())) {
            throw new RuntimeException("Email já cadastrado");
        }

        if (userRepository.existsByNome(userDto.nome())) {
            throw new RuntimeException("Nome já cadastrado");
        }

        UserTable user = new UserTable();
        user.setEmail(userDto.email());
        user.setNome(userDto.nome());
        user.setRole(userDto.role());
        user.setUserImg(userDto.userImg());
        user.setSenha(passwordEncoder.encode(userDto.senha()));
        user.setDataAdmissao(LocalDateTime.now());
        userRepository.save(user);
    }

    public UserTable monitoraPorId(Long id) {
        UserTable monitora = userRepository.findByUsuarioId(id);
        return monitora;

    }

    public void AtualizarUser(UserDtoUpload dto) {
        UserTable user = userRepository.findByUsuarioId(dto.id());
        if (dto.senha() != null && !dto.senha().isBlank()) {
            user.setSenha(dto.senha());
        }
        if (dto.nome() != null) {
            user.setNome(dto.nome());
        }
        if (dto.email() != null) {
            user.setEmail(dto.email());
        }
        if (dto.senha() != null) {
            user.setSenha(dto.senha());
        }
        userRepository.save(user);
    }
}
