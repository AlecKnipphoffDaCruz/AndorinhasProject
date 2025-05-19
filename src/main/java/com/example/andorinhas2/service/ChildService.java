package com.example.andorinhas2.service;

import com.example.andorinhas2.dto.ChildDto;
import com.example.andorinhas2.dto.UserDto;
import com.example.andorinhas2.model.ChildTable;
import com.example.andorinhas2.model.UserTable;
import com.example.andorinhas2.repository.ChildRepository;
import com.example.andorinhas2.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildService {
    private ChildRepository childRepository;

    public ChildService(ChildRepository childRepository){
        this.childRepository = childRepository;
    }

    public List<ChildTable> listarTodos(){
        return childRepository.findAll();
    }
    @Transactional
    public void cadastrar(ChildDto childDto) {
        childRepository.save(new ChildTable(childDto));
    }


    public ChildTable listarCrianca(Long id) {
        return childRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Criança não encontrada"));
    }
    public Long totalCrianças(){
        Long total;
         total = childRepository.count();
        return total;
    }
}
