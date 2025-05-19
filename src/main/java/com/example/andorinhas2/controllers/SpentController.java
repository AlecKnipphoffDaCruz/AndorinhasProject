package com.example.andorinhas2.controllers;

import com.example.andorinhas2.dto.SpentDto;
import com.example.andorinhas2.model.SpentTable;
import com.example.andorinhas2.repository.SpentRepository;
import com.example.andorinhas2.service.SpentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/despesa")
public class SpentController {
    @Autowired
    private SpentRepository spentRepository;
    private SpentService spentService;
    public SpentController (SpentRepository spentRepository, SpentService spentService){
        this.spentService = spentService;
        this.spentRepository = spentRepository;
    }

    @PostMapping
    public ResponseEntity<String> cadastrarNovaDespesa (@RequestBody SpentDto dto){
        spentService.cadastrarNovaDespesa(dto);
                return ResponseEntity.ok("Despesa cadastrada com sucesso!");
    }
    @GetMapping
    public List<SpentTable> listarTodasAsDespesas (){
        List<SpentTable> listaDespesas =  spentService.listarTodasDespesas();
        return listaDespesas;
}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarDespesa (@PathVariable Long id){
        Optional<SpentTable> entidadeOptional = spentRepository.findById(id);
        if (entidadeOptional.isPresent()){
            SpentTable entity = entidadeOptional.get();
        spentRepository.deleteById(id);
        return ResponseEntity.ok("Despesa:  --" + entity.getDescricao() + "-- removida com sucesso");
    }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entidade não encontrada!");
        }
    }
}
