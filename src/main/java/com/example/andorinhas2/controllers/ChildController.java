package com.example.andorinhas2.controllers;

import com.example.andorinhas2.dto.ChildANDMonthlyDto;
import com.example.andorinhas2.dto.ChildDto;
import com.example.andorinhas2.model.ChildTable;
import com.example.andorinhas2.model.MonthlyTable;
import com.example.andorinhas2.repository.ChildRepository;
import com.example.andorinhas2.repository.UserRepository;
import com.example.andorinhas2.service.ChildService;
import com.example.andorinhas2.service.MonthlyService;
import com.example.andorinhas2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@RestController
@RequestMapping ("/crianca")
public class ChildController {
    private ChildRepository childRepository;
    private ChildService childService;
    private MonthlyService monthlyService;

    public ChildController(ChildRepository childRepository, ChildService childService, MonthlyService monthlyService) {
        this.childRepository = childRepository;
        this.childService = childService;
        this.monthlyService = monthlyService;
    }

    @PostMapping
    public ResponseEntity<String> postarCrianca(@RequestBody @Valid ChildDto childDto) {
        childService.cadastrar(childDto);
        return ResponseEntity.ok("Criança cadastrada com sucesso!");
    }

    @GetMapping
    public List<ChildTable> listarTodasCriancas() {

        return childService.listarTodos();
    }

    @GetMapping("total/matriculas")
    public ResponseEntity totalMatriculas (){
        Long total = childService.totalCriancas();

        return ResponseEntity.ok(total);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ChildANDMonthlyDto> listarCrianca(@PathVariable Long id){
        ChildTable crianca = childService.listarCrianca(id);
        MonthlyTable mensalidadeCrianca = monthlyService.mensalidadeDaCrianca(id);


        ChildANDMonthlyDto response = new ChildANDMonthlyDto(crianca, mensalidadeCrianca);


        return ResponseEntity.ok(response);
    }

    @PutMapping("/exclusao/{id}")
    public ResponseEntity<String> exclusaoLogica(@PathVariable Long id){
        childService.excluir(id);
        return ResponseEntity.ok("Exclusão lógica concluida!");
    }
}
