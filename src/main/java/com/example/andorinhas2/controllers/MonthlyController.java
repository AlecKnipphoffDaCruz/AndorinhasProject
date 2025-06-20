package com.example.andorinhas2.controllers;

import com.example.andorinhas2.dto.MonthlyDto;
import com.example.andorinhas2.model.ChildTable;
import com.example.andorinhas2.model.MonthlyTable;
import com.example.andorinhas2.repository.ChildRepository;
import com.example.andorinhas2.repository.MonthlyRepository;
import com.example.andorinhas2.service.MonthlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensalidade")
public class MonthlyController {

    private final MonthlyRepository monthlyRepository;
    private final MonthlyService monthlyService;
    private final ChildRepository childRepository;
    @Autowired
    public MonthlyController(MonthlyRepository monthlyRepository, MonthlyService monthlyService, ChildRepository childRepository) {
        this.monthlyRepository = monthlyRepository;
        this.monthlyService = monthlyService;
        this.childRepository = childRepository;
    }


    @GetMapping
    public List<MonthlyTable> listarMensaliade (@PathVariable(required = false)Long criancaId, @PathVariable(required = false)Integer mes, @PathVariable(required = false)Integer ano){
        if (criancaId != null) {
            return monthlyRepository.findByCriancaId(criancaId);
        } else if (mes != null && ano != null) {
            return monthlyRepository.findByMesEAno(mes, ano);
        } else {
            return monthlyRepository.findAll();
        }
    }
    @GetMapping ("/pendente")
    public ResponseEntity<List<MonthlyTable>> listarPendentes (){
        List<MonthlyTable> pendete =  monthlyService.pendentes();
        return ResponseEntity.ok(pendete);
    }
    @GetMapping("/quantidade/pendente")
    public ResponseEntity<Long> quantidadeDePendetes(){
        Long total = monthlyService.quantidadeDePendentes();
        return ResponseEntity.ok(total);
    }

    @PostMapping
    public ResponseEntity<String> criar(@RequestBody MonthlyDto dto) {
        monthlyService.criarMensalidade(dto);
        return ResponseEntity.ok("Mensalidade criada com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> pagarPorCrianca(@PathVariable Long id) {
        monthlyService.marcarComoPagaPorCrianca(id);
        return ResponseEntity.ok("Mensalidade da criança marcada como paga");
    }

    @GetMapping("/por/{id}")
    public ResponseEntity<MonthlyTable> getPorId(@PathVariable Long id){
       List<MonthlyTable> lista = monthlyRepository.findByCriancaId(id);

       MonthlyTable crianca = lista.get(lista.lastIndexOf(lista));

       return ResponseEntity.ok(crianca);
    }


    @PutMapping("/{id}/{valor}")
    public ResponseEntity<String> atualizarValorMensalidade(@PathVariable Long id, @PathVariable Long valor) {
        monthlyService.atualizarValorMensalidade(id, valor);
        return ResponseEntity.ok("Valor atualizado");
    }
    @PostMapping("/criar-automaticamente")
    public ResponseEntity<String> criarMensalidadesAutomaticas() {
        try {
            monthlyService.criarMensalidadesParaTodos();
            return ResponseEntity.ok("Mensalidades criadas para todas as crianças com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar mensalidades: " + e.getMessage());
        }
    }
}
