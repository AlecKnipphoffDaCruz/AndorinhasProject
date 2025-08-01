package com.example.andorinhas2.controllers;

import com.example.andorinhas2.dto.AuthDto;
import com.example.andorinhas2.dto.TokenDto;
import com.example.andorinhas2.model.UserTable;
import com.example.andorinhas2.service.AuthService;
import com.example.andorinhas2.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/auth/login")
@RestController
public class AuthController {

    private AuthenticationManager manager;
    private AuthService authService;
    private TokenService tokenService;

    AuthController(AuthService authService, AuthenticationManager manager, TokenService tokenService){
        this.authService = authService;
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity efetuarlogin(@RequestBody AuthDto dto){
        var authToken = new UsernamePasswordAuthenticationToken(dto.email(),dto.senha());
        var authentication= manager.authenticate(authToken);
        var tokenJWT = tokenService.gererToken((UserTable) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDto(tokenJWT));
    } 
    
}
