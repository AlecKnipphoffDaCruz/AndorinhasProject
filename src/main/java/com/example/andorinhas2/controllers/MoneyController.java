package com.example.andorinhas2.controllers;


import com.example.andorinhas2.model.IncomesPayedTable;
import com.example.andorinhas2.repository.IncomePayedRespository;
import com.example.andorinhas2.service.IncomeService;
import com.example.andorinhas2.service.SpentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/money")
@RestController
public class MoneyController {

    @Autowired
    private IncomePayedRespository repository;

    @Autowired
    private IncomeService service;

    @Autowired
    private SpentService spentService;

    @Autowired

    @GetMapping("/gasto")
    public ResponseEntity<Long> TotalGasto() {
        Long valorTotal = spentService.ValorTodasDespesas();

        return ResponseEntity.ok(valorTotal);
    }

    @GetMapping("/ganho")
    public ResponseEntity<Long> totalGanho() {
        Long valorTotal = service.valorGanho30dias();
        return ResponseEntity.ok(valorTotal);

    }
}
