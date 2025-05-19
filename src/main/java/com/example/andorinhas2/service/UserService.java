package com.example.andorinhas2.service;

import com.example.andorinhas2.dto.UserDto;
import com.example.andorinhas2.model.UserTable;
import com.example.andorinhas2.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public List<UserTable> listarTodos(){
        return userRepository.findAll();
    }
    @Transactional
    public void cadastrar(UserDto userDto) {
        UserTable user = new UserTable();
        user.setEmail(userDto.email());
        user.setNome(userDto.nome());
        user.setRole(userDto.role());
        user.setSenha(passwordEncoder.encode(userDto.senha()));
        userRepository.save(user);

    }
}
