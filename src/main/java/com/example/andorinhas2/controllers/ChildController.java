package com.example.andorinhas2.controllers;

import com.example.andorinhas2.dto.ChildDto;
import com.example.andorinhas2.model.ChildTable;
import com.example.andorinhas2.repository.ChildRepository;
import com.example.andorinhas2.service.ChildService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping ("/crianca")
public class ChildController {
    private ChildRepository childRepository;
    private ChildService childService;

    public ChildController(ChildRepository childRepository, ChildService childService) {
        this.childRepository = childRepository;
        this.childService = childService;
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


    @PutMapping("/exclusao/{id}")
    public ResponseEntity<String> exclusaoLogica(@PathVariable Long id){
        childService.excluir(id);
        return ResponseEntity.ok("Exclusão lógica concluida!");
    }

    @PutMapping("/reativar/{id}")
    public ResponseEntity<String> reativacao(@PathVariable Long id){
        childService.reativar(id);
        return ResponseEntity.ok("Restauração concluida!");
    }

    @PutMapping("atualizar")
    public ResponseEntity<String> atualizacaoDados(@RequestBody ChildDto dto){
        childService.atualizar(dto);
        return ResponseEntity.ok("Atualização feita com sucesso");
    }
    @GetMapping("ativas")
    public List<ChildTable> listaAtivas(){
        List<ChildTable> lista = childService.acharAtivas(true);

        return lista;
    }
    @GetMapping("desativas")
    public List<ChildTable> listaDesativadas(){
        List<ChildTable> lista = childService.acharAtivas(false);
        return lista;
    }

    @GetMapping("quantidade/ativas")
    public ResponseEntity<Long> totalAtivas(){
        Long lista = childService.quantidadeAtivas(true);
        return ResponseEntity.ok(lista);
    }

}
