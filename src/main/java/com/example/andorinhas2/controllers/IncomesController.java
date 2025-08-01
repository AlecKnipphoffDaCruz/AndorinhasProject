package com.example.andorinhas2.controllers;

import com.example.andorinhas2.dto.IncomeDto;
import com.example.andorinhas2.model.IncomesPayedTable;
import com.example.andorinhas2.repository.IncomeExpRepository;
import com.example.andorinhas2.repository.IncomePayedRespository;
import com.example.andorinhas2.repository.IncomeRepository;
import com.example.andorinhas2.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("income")
public class IncomesController {

    /*
     * 2-diaria
     * 3-mensalidade
     * 4-colonia de ferias
     * */

    @Autowired
    private IncomeExpRepository expRepository;

    @Autowired
    private IncomeService service;

    @Autowired
    private IncomePayedRespository payedRespository;

    @Autowired
    private IncomeRepository incomeRepository;


    @PostMapping("/{id}")
    public ResponseEntity<String> create(@PathVariable Long id, IncomeDto dto){
        service.createIncome(id, dto);
        return ResponseEntity.ok("Criado!");
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<IncomesPayedTable>> getAllbyId(@PathVariable Long id){
        List<IncomesPayedTable> list = payedRespository.findAllByCriancaId(id);
        return ResponseEntity.ok(list);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> payIncome(@PathVariable Long id){
        service.payIncome(id);
        return ResponseEntity.ok("ok!");
    }
}
