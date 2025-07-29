package com.example.andorinhas2.controllers;

import com.example.andorinhas2.dto.UserDto;
import com.example.andorinhas2.dto.UserDtoUpload;
import com.example.andorinhas2.model.Erole;
import com.example.andorinhas2.model.UserTable;
import com.example.andorinhas2.repository.UserRepository;
import com.example.andorinhas2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @GetMapping("img/{id}")
    public ResponseEntity<String> getImg(@PathVariable Long id){
        UserTable user = userRepository.findByUsuarioId(id);
        String img = user.getUserImg();
        return ResponseEntity.ok(img);
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<String> removerMonitora(@PathVariable Long usuarioId){
        userRepository.deleteByUsuarioId(usuarioId);
        return ResponseEntity.ok("Monitora removida com suceso!");
    }

    @PutMapping("/config")
    public ResponseEntity<String> atualizarUser(@RequestBody UserDtoUpload userDto){
        userService.AtualizarUser(userDto);
        return ResponseEntity.ok("Atulização feita com sucesso!");
    }

    @PreAuthorize("ROLE_ADMIN")
    @PutMapping("/roleToAdmin/{usuarioId}")
    public ResponseEntity<String> atualizarRole(@PathVariable Long usuarioId){
        UserTable user = userRepository.findByUsuarioId(usuarioId);
        user.setRole(Erole.ROLE_ADMIN);
        return ResponseEntity.ok("Atualização feita com sucesso!");
    }
    @PreAuthorize("ROLE_ADMIN")
    @PutMapping("/roleToMonitora/{usuarioId}")
    public ResponseEntity<String> atualizarRole2(@PathVariable Long usuarioId){
        UserTable user = userRepository.findByUsuarioId(usuarioId);
        user.setRole(Erole.ROLE_MONITORA);
        return ResponseEntity.ok("Atualização feita com sucesso!");
    }

}
