package com.example.andorinhas2.controllers;

import com.example.andorinhas2.dto.UserDto;
import com.example.andorinhas2.model.UserTable;
import com.example.andorinhas2.repository.UserRepository;
import com.example.andorinhas2.service.UserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UsersController {

    private UserRepository userRepository;
    private UserService userService;

    public UsersController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> cadastrarMonitora (@RequestBody @Valid UserDto userDto){
        userService.cadastrar(userDto);
        return ResponseEntity.ok("Monitora cadastrada com sucesso!");
    }

    @GetMapping
    public List<UserTable> mostrarMonitoras (){
        return userService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTable> mostrarMonitora (@PathVariable Long id){
        UserTable monitora = userService.monitoraPorId(id);
        return ResponseEntity.ok(monitora);
    }


    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<String> removerMonitora(@PathVariable Long usuarioId){
        userRepository.deleteByUsuarioId(usuarioId);
        return ResponseEntity.ok("Monitora removida com suceso!");
    }
}
