package com.example.andorinhas2.controllers;

import com.example.andorinhas2.model.IncomeTable;
import com.example.andorinhas2.model.IncomesPayedTable;
import com.example.andorinhas2.service.IncomeService;
import com.example.andorinhas2.service.SpentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/excel")
public class ExcelDataController {

    @Autowired
    private SpentService spentService;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Gera Excel com entradas pendentes (incomes)
     */
    @PostMapping("/incomes/{mes}/{ano}")
    public ResponseEntity<?> generateIncomesExcel(
            @PathVariable Integer mes,
            @PathVariable Integer ano,
            @RequestParam(defaultValue = "IncomesPendentes") String tag) {

        try {
            List<IncomeTable> list = incomeService.findByMonthAndYear(mes, ano);

            Map<String, Object> requestData = new HashMap<>();
            requestData.put("tag", tag);
            requestData.put("month", mes);
            requestData.put("year", ano);
            requestData.put("tipo", "incomes");
            requestData.put("data", list);

            return sendRequestToPython(requestData);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Erro ao processar dados: " + e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Erro interno: " + e.getMessage()));
        }
    }

    /**
     * Gera Excel com entradas pagas (incomes pagos)
     */
    @PostMapping("/incomes-payed/{mes}/{ano}")
    public ResponseEntity<?> generateIncomesPayedExcel(
            @PathVariable Integer mes,
            @PathVariable Integer ano,
            @RequestParam(defaultValue = "IncomesPagos") String tag) {

        try {
            List<IncomesPayedTable> list = incomeService.findByMonthAndYearOnPayed(mes, ano);

            Map<String, Object> requestData = new HashMap<>();
            requestData.put("tag", tag);
            requestData.put("month", mes);
            requestData.put("year", ano);
            requestData.put("tipo", "incomes_payed");
            requestData.put("data", list);

            return sendRequestToPython(requestData);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Erro ao processar dados: " + e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Erro interno: " + e.getMessage()));
        }
    }

    /**
     * Gera Excel com gastos
     */
    @PostMapping("/spent/{mes}/{ano}")
    public ResponseEntity<?> generateSpentExcel(
            @PathVariable Integer mes,
            @PathVariable Integer ano,
            @RequestParam(defaultValue = "Gastos") String tag) {

        try {
            List<?> list = spentService.todasDespesasPorData(mes, ano);

            Map<String, Object> requestData = new HashMap<>();
            requestData.put("tag", tag);
            requestData.put("month", mes);
            requestData.put("year", ano);
            requestData.put("tipo", "spent");
            requestData.put("data", list);

            return sendRequestToPython(requestData);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Erro interno: " + e.getMessage()));
        }
    }

    /**
     * Gera Excel com balanço financeiro geral
     */
    @PostMapping("/balance/{mes}/{ano}")
    public ResponseEntity<?> generateBalanceExcel(
            @PathVariable Integer mes,
            @PathVariable Integer ano,
            @RequestParam(defaultValue = "Balanco") String tag) {

        try {
            // Exemplo: você pode agregar dados de incomes, incomes pagos e gastos para enviar no relatório
            Map<String, Object> requestData = new HashMap<>();
            requestData.put("tag", tag);
            requestData.put("month", mes);
            requestData.put("year", ano);
            requestData.put("tipo", "balance");
            // requestData.put("data", algumResumoFinanceiro);

            return sendRequestToPython(requestData);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Erro interno: " + e.getMessage()));
        }
    }

    /**
     * Método auxiliar para enviar dados ao serviço Python e retornar resposta
     */
    private ResponseEntity<?> sendRequestToPython(Map<String, Object> requestData) throws JsonProcessingException {
        String urlPython = "http://localhost:5000/api/receber-lista";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(requestData);

        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(urlPython, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();

            return ResponseEntity.ok().body(Map.of(
                    "success", true,
                    "message", "Excel gerado com sucesso!",
                    "arquivo", responseBody.get("arquivo"),
                    "conteudo", responseBody.get("conteudo"),
                    "tipo", responseBody.get("tipo")
            ));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Erro ao gerar Excel"));
        }
    }
}
