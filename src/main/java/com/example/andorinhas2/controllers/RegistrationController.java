package com.example.andorinhas2.controllers;

import com.example.andorinhas2.model.ChildTable;
import com.example.andorinhas2.model.ERegistration;
import com.example.andorinhas2.model.RegistrationTable;
import com.example.andorinhas2.model.SemanaModel;
import com.example.andorinhas2.repository.RegistrationRepository;
import com.example.andorinhas2.service.ChildService;
import com.example.andorinhas2.service.RegistrationService;
import jakarta.persistence.GeneratedValue;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.sql.exec.internal.JdbcCallParameterRegistrationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/registro")
public class RegistrationController {
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private RegistrationService registrationService;


    @PostMapping("/{criancaId}/{tipo}")
    public ResponseEntity<String> registrarEntradaSaida(@PathVariable Long criancaId, @PathVariable ERegistration tipo){
        registrationService.registrarEntradaSaida(criancaId, tipo);
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String horarioFormatado = agora.format(formatter);
        return ResponseEntity.ok("Registro de "+ tipo.name()+" realizado com sucesso! DATA E HORARIO: " + horarioFormatado);
    }
    @GetMapping("/historico/{crianca}")
    public List<RegistrationTable> historico (@PathVariable String crianca){
        return registrationService.getHistoricoPorNome(crianca);
    }
    @GetMapping("/presentes")
    public ResponseEntity<List<ChildTable>> getCriancasPresentes() {
        List<ChildTable> presentes = registrationService.listarCriancasPresentes();
        return ResponseEntity.ok(presentes);
    }
    @GetMapping("total/hoje")
    public ResponseEntity<Long> getTotalDePresentesHoje(){
       Long total = registrationService.presencasHoje();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/total/semana")
    public ResponseEntity<SemanaModel> presencaSemana(){

        SemanaModel total = registrationService.presencasSemana();

        return ResponseEntity.ok(total);
    }
}
