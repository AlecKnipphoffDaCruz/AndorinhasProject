package com.example.andorinhas2.controllers;

import com.example.andorinhas2.service.FinalRelatoryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/relatorio")
public class FinalRelatoryController {
    private FinalRelatoryService finalRelatoryService;

    public FinalRelatoryController (FinalRelatoryService finalRelatoryService){
        this.finalRelatoryService = finalRelatoryService;
    }

    @GetMapping("/mensal/{mes}")
    public ResponseEntity<byte[]> baixarRelatorio(@PathVariable("mes") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate mes) throws IOException {
        byte[] relatorio = finalRelatoryService.gerarRelatorioMensal(mes);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_" + mes.getMonthValue() + "_" + mes.getYear() + ".docx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(relatorio);
    }
}
