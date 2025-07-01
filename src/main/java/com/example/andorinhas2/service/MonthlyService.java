package com.example.andorinhas2.service;

import com.example.andorinhas2.dto.MonthlyDto;
import com.example.andorinhas2.model.ChildTable;
import com.example.andorinhas2.model.ERegistration;
import com.example.andorinhas2.model.MonthlyTable;
import com.example.andorinhas2.repository.ChildRepository;
import com.example.andorinhas2.repository.MonthlyRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonthlyService {
    private MonthlyRepository monthlyRepository;
    private ChildRepository childRepository;

    public MonthlyTable mensalidadeDaCrianca(Long id) {
        List<MonthlyTable> mensalidades = monthlyRepository.findByCriancaId(id);

        if (mensalidades.isEmpty()) {
            throw new RuntimeException("Nenhuma mensalidade encontrada para essa criança");
        }

        return mensalidades.get(0);
    }


    public MonthlyService(MonthlyRepository monthlyRepository, ChildRepository childRepository) {
        this.monthlyRepository = monthlyRepository;
        this.childRepository = childRepository;
    }


    public List<MonthlyTable> pendentes(){
        List<MonthlyTable> pendete = new ArrayList<>();
        List<MonthlyTable> todasMensalidade = monthlyRepository.findAll();
        for (MonthlyTable c : todasMensalidade){
            if (!c.getEstaPaga()) {
                pendete.add(c);
            }
        }

        return pendete;
    }
    public Long quantidadeDePendentes(){
        Long total = monthlyRepository.countByDataPagamentoIsNull();
        return total;
    }

    public MonthlyTable criarMensalidade(MonthlyDto dto) {
        System.out.println(dto);

        if (dto.crianca_id() == null) {
            throw new IllegalArgumentException("O ID da criança não pode ser nulo");
        }
        MonthlyTable ultimaMensalidade = monthlyRepository.findTopByCriancaIdOrderByDataVencimentoDesc(dto.crianca_id());
        ChildTable crianca = childRepository.findById(dto.crianca_id()).orElseThrow(() -> new RuntimeException("Criança não encontrada"));
        LocalDate proximoMes = ultimaMensalidade.getDataVencimento().plusMonths(1);


        MonthlyTable mensalidade = new MonthlyTable();
        mensalidade.setCrianca(crianca);
        mensalidade.setValor(dto.valor());
        mensalidade.setDataVencimento(proximoMes);
        mensalidade.setEstaPaga(false);
        mensalidade.setDataPagamento(null);

        return monthlyRepository.save(mensalidade);
    }

    public MonthlyTable marcarComoPagaPorCrianca(Long criancaId) {
        MonthlyTable mensalidade = monthlyRepository
                .findFirstByCriancaIdAndEstaPagaFalseOrderByDataVencimentoAsc(criancaId)
                .orElseThrow(() -> new RuntimeException("Nenhuma mensalidade pendente encontrada para esta criança"));

        mensalidade.setEstaPaga(true);
        mensalidade.setDataPagamento(LocalDate.now());

        return monthlyRepository.save(mensalidade);
    }
    public MonthlyTable atualizarValorMensalidade(Long criancaId, Long valorNovo) {
        MonthlyTable crianca = monthlyRepository.findFirstByCriancaId(criancaId);

        if (crianca == null) {
            throw new RuntimeException("Nenhuma criança encontrada para o id: " + criancaId);
        }

        crianca.setValor(valorNovo);
        crianca.setEstaPaga(false);
        crianca.setDataPagamento(null);
        return monthlyRepository.save(crianca);
    }

    public void criarMensalidadesAutomaticasParaOMes(Long criancaId) {
        MonthlyTable ultimaMensalidade = monthlyRepository.findTopByCriancaIdOrderByDataVencimentoDesc(criancaId);


        if (ultimaMensalidade == null) {
            throw new RuntimeException("Não há mensalidade anterior para a criança com ID " + criancaId);
        }


        LocalDate proximoMes = ultimaMensalidade.getDataVencimento().plusMonths(1);

        MonthlyTable novaMensalidade = new MonthlyTable();
        novaMensalidade.setCrianca(ultimaMensalidade.getCrianca());
        novaMensalidade.setValor(ultimaMensalidade.getValor());
        novaMensalidade.setDataVencimento(proximoMes);
        novaMensalidade.setEstaPaga(false);
        novaMensalidade.setDataPagamento(null);

        monthlyRepository.save(novaMensalidade);
    }
    @Scheduled(cron = "0 0 0 1 * ?")  // INDICA QUANDO VAI SER REALIZADA A CRIAÇÃO AUTOMATICA
    public void criarMensalidadesParaTodos() {
        List<ChildTable> criancas = childRepository.findAll();

        for (ChildTable crianca : criancas) {
            criarMensalidadesAutomaticasParaOMes(crianca.getId());
        }
    }
    public Long valorGanho30dias(){
        long valor = 0;
        List<MonthlyTable> lista = monthlyRepository.findUltimos30DiasPagosNative();
        for (MonthlyTable c : lista){
            valor += c.getValor();
        }
        return valor;
    }

   }
