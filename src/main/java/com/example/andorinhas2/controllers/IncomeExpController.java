package com.example.andorinhas2.controllers;

import com.example.andorinhas2.dto.IncomeDto;
import com.example.andorinhas2.model.IncomeExpModel;
import com.example.andorinhas2.repository.IncomeExpRepository;
import com.example.andorinhas2.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/exp/income")
public class IncomeExpController {

    @Autowired
    private IncomeExpRepository respository;

    @Autowired
    private IncomeService service;

    /*
    * 2-diaria
    * 3-mensalidade
    * 4-colonia de ferias
    * */


    @PutMapping("/{id}/{newValue}")
    public ResponseEntity<String> changeValue (@PathVariable Long id, @PathVariable Long newValue){
        IncomeExpModel model = respository.getReferenceById(id);
        model.setValue(newValue);
        respository.save(model);

        return ResponseEntity.ok("Metodo efetuado com sucesso!");
    }

    @PostMapping("/{value}")
    public ResponseEntity<String> createEscificRule (@PathVariable Long value){
        IncomeExpModel model = new IncomeExpModel();
        model.setValue(value);
        respository.save(model);
        return ResponseEntity.ok("ok!");
    }

    @GetMapping
    public ResponseEntity<Long> totalIncomePending (){
        return ResponseEntity.ok((service.mensalidadesPendentes()));
    }
}
