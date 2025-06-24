package com.example.andorinhas2.service;

import com.example.andorinhas2.model.ChildTable;
import com.example.andorinhas2.model.ERegistration;
import com.example.andorinhas2.model.RegistrationTable;
import com.example.andorinhas2.model.SemanaModel;
import com.example.andorinhas2.repository.ChildRepository;
import com.example.andorinhas2.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registroRepository;

    @Autowired
    private ChildRepository childRepository;

    public RegistrationTable registrarEntradaSaida(Long criancaId, ERegistration tipo) {
        ChildTable crianca = childRepository.findById(criancaId).orElseThrow(() -> new RuntimeException("Criança não encontrada"));
        crianca.setERegistration(tipo);
        childRepository.save(crianca);
        RegistrationTable registro = new RegistrationTable(crianca, tipo, LocalDateTime.now());
        return registroRepository.save(registro);
    }

    public List<RegistrationTable> getHistoricoPorNome(String nome) {

        List<RegistrationTable> historico = registroRepository.findByCriancaNome(nome);

        return historico;
    }

    public List<RegistrationTable> getPresentes() {
        return registroRepository.findByTipo(ERegistration.ENTRADA);
    }
    public List<ChildTable> listarCriancasPresentes() {
        List<ChildTable> todasAsCriancas = childRepository.findAll();
        List<ChildTable> presentes = new ArrayList<>();

        for (ChildTable c : todasAsCriancas) {
            Optional<RegistrationTable> ultimoRegistro = registroRepository.findTopByCriancaOrderByDatahoraDesc(c);
            if (ultimoRegistro.isPresent() && ultimoRegistro.get().getTipo() == ERegistration.ENTRADA) {
                presentes.add(c);
            }
        }

        return presentes;
    }
    public Long presencasHoje(){
        LocalDate hoje = LocalDate.now();
        LocalDateTime inicio = hoje.atTime(LocalTime.of(6, 0));
        LocalDateTime fim = hoje.atTime(LocalTime.of(20, 0));
        List<RegistrationTable> totalList = registroRepository.findByDatahoraBetweenAndTipo(inicio, fim, ERegistration.ENTRADA);
        Long total = (long) totalList.size();

        return total;
    }

    public SemanaModel presencasSemana() {
        SemanaModel semana = new SemanaModel();

        for (int i = 0; i < 7; i++) {
            LocalDate dia = LocalDate.now().minusDays(i);
            LocalDateTime inicio = dia.atTime(LocalTime.of(6, 0));
            LocalDateTime fim = dia.atTime(LocalTime.of(20, 0));

            List<RegistrationTable> totalList = registroRepository.findByDatahoraBetweenAndTipo(
                    inicio, fim, ERegistration.ENTRADA);

            int quantidade = totalList.size();

            switch (i) {
                case 0 -> semana.setSegunda(quantidade);
                case 1 -> semana.setTerca(quantidade);
                case 2 -> semana.setQuarta(quantidade);
                case 3 -> semana.setQuinta(quantidade);
                case 4 -> semana.setSexta(quantidade);
                case 5 -> semana.setSabado(quantidade);
                case 6 -> semana.setDomingo(quantidade);
            }
        }

        return semana;
    }

}