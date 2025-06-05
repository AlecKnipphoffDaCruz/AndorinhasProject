package com.example.andorinhas2.service;
import com.example.andorinhas2.dto.SpentDto;
import com.example.andorinhas2.model.SpentTable;
import com.example.andorinhas2.repository.SpentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class SpentService {
    @Autowired
    private SpentRepository spentRepository;

    public SpentTable cadastrarNovaDespesa(SpentDto dto) {
        SpentTable despesa = new SpentTable();
        despesa.setData(LocalDate.now());
        despesa.setDescricao(dto.descricao());
        despesa.setValor(dto.valor());
        despesa.setTipo(dto.tipo());
        return spentRepository.save(despesa);
    }
    public List<SpentTable> listarTodasDespesas (){
        List<SpentTable> todasAsDespesas = spentRepository.findAll();
        return todasAsDespesas;
    }
}

