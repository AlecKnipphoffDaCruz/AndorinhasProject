package com.example.andorinhas2.service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.andorinhas2.model.UserTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${api.service.tokenService.secretPassword}")
    private String secretPassword;

    public String gererToken(UserTable userTable){
        try {
            var now = new Date();
            var expiresAt = new Date(now.getTime() + 6 * 60 * 60 * 1000);
            var algorithm = Algorithm.HMAC256(secretPassword);
            return JWT.create()
                    .withIssuer("API andorinhas")
                    .withSubject(userTable.getEmail())
                    .withClaim("name",userTable.getNome())
                    .withClaim("roles", userTable.getRole().toString())
                    .withClaim("img", userTable.getUserImg())
                    .withClaim("id", userTable.getUsuarioId())
                    .withIssuedAt(now)
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw  new RuntimeException("Erro ao gerar TOKEN-JWT", exception);
        }
    }
    public String getSubject (String tokenJWT) {
        try {
            var algorithm = Algorithm.HMAC256(secretPassword);
            return  JWT.require(algorithm)
                    .withIssuer("API andorinhas")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT invalido ou expirado!");
        }
    }

}
