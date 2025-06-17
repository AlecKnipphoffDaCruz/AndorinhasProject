package com.example.andorinhas2.controllers;


import com.example.andorinhas2.service.MonthlyService;
import com.example.andorinhas2.service.SpentService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/money")
@RestController
public class MoneyController {


    @Autowired
    private SpentService spentService;

    @Autowired
    private MonthlyService monthlyService;

    @GetMapping("/gasto")
    public ResponseEntity<Long> TotalGasto(){
        Long valorTotal = spentService.ValorTodasDespesas();

        return ResponseEntity.ok(valorTotal);
    }

    @GetMapping("/ganho")
    public ResponseEntity<Long> TotalGanho(){
        Long valorTotal = monthlyService.valorGanho30dias();

        return ResponseEntity.ok(valorTotal);
    }

}
