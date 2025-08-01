package com.example.andorinhas2.service;

import com.example.andorinhas2.dto.IncomeDto;
import com.example.andorinhas2.model.ChildTable;
import com.example.andorinhas2.model.IncomeExpModel;
import com.example.andorinhas2.model.IncomeTable;
import com.example.andorinhas2.model.IncomesPayedTable;
import com.example.andorinhas2.repository.ChildRepository;
import com.example.andorinhas2.repository.IncomeExpRepository;
import com.example.andorinhas2.repository.IncomePayedRespository;
import com.example.andorinhas2.repository.IncomeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDate;

@Service
public class IncomeService {

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private IncomeExpRepository expRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private IncomePayedRespository payedRepository;

    public void createIncome(Long id, IncomeDto dto){
        IncomeExpModel exp = expRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income example not found with id: " + id));
        ChildTable kid = childRepository.getReferenceById(dto.criancaId());


        IncomeTable newIncome = new IncomeTable();
        newIncome.setDays(dto.dias());
        newIncome.setDateCreation(LocalDate.now());
        newIncome.setIsPaga(false);
        newIncome.setCrianca(kid);
        newIncome.setValue(exp.getValue());
        newIncome.setPaymentType(dto.tipoPagamento());

        incomeRepository.save(newIncome);
    }

    @Transactional
    public void payIncome(Long id){
        IncomeTable income = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found with id: " + id));

        ChildTable kid = childRepository.getReferenceById(id);

        IncomesPayedTable incomePayed = new IncomesPayedTable();
        incomePayed.setDatePayment(LocalDate.now());
        incomePayed.setValue(income.getValue());
        incomePayed.setDays(income.getDays());
        incomePayed.setPaymentType(income.getPaymentType());
        incomePayed.setIsPaga(true);
        incomePayed.setCrianca(kid);
        incomePayed.setDateCreation(income.getDateCreation());

        payedRepository.save(incomePayed);
        incomeRepository.deleteById(id);
    }

    public List<IncomeTable> findByMonthAndYear(int month, int year) {
        return incomeRepository.findByMonthAndYear(month, year);
    }

    public List<IncomesPayedTable> findByMonthAndYearOnPayed(int month, int year) {
        return payedRepository.findByMonthAndYear(month, year);
    }

    public Long valorGanho30dias() {
        LocalDate hoje = LocalDate.now();
        LocalDate trintaDiasAtras = hoje.minusDays(30);
        return payedRepository.sumValueByDatePaymentBetween(trintaDiasAtras, hoje);
    }
}

