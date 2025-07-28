package com.example.andorinhas2.controllers;


import com.example.andorinhas2.model.MonthlyTable;
import com.example.andorinhas2.model.SpentTable;
import com.example.andorinhas2.service.MonthlyService;
import com.example.andorinhas2.service.SpentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;
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
        private MonthlyService monthlyService;

        @Autowired
        private RestTemplate restTemplate;

        @PostMapping("/month/{mes}/{ano}")
        public ResponseEntity<?> generateMonthExcel(
                @PathVariable Integer mes,
                @PathVariable Integer ano,
                @RequestParam(defaultValue = "month") String tipo,
                @RequestParam(defaultValue = "Relatorio") String tag) {

            try {
                // Busca dados no banco
                List<MonthlyTable> list = monthlyService.todasCriadas(mes, ano);

                // Prepara dados para o Python
                Map<String, Object> requestData = new HashMap<>();
                requestData.put("tag", tag);
                requestData.put("month", mes);
                requestData.put("year", ano);
                requestData.put("tipo", tipo);
                requestData.put("data", list);

                // Configuração da requisição
                String urlPython = "http://localhost:5000/api/receber-lista";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Converte para JSON
                ObjectMapper mapper = new ObjectMapper();
                String jsonBody = mapper.writeValueAsString(requestData);

                HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

                // Chama o serviço Python
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

        @PostMapping("/spent/{mes}/{ano}")
        public ResponseEntity<?> generateSpentExcel(
                @PathVariable Integer mes,
                @PathVariable Integer ano,
                @RequestParam(defaultValue = "Gastos") String tag) {

            try {
                // Aqui você buscaria os dados de gastos
                // List<SpentTable> list = spentService.buscarGastos(mes, ano);

                Map<String, Object> requestData = new HashMap<>();
                requestData.put("tag", tag);
                requestData.put("month", mes);
                requestData.put("year", ano);
                requestData.put("tipo", "spent");
                // requestData.put("data", list);

                String urlPython = "http://localhost:5000/api/receber-lista";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                ObjectMapper mapper = new ObjectMapper();
                String jsonBody = mapper.writeValueAsString(requestData);

                HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
                ResponseEntity<Map> response = restTemplate.postForEntity(urlPython, request, Map.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    return ResponseEntity.ok().body(response.getBody());
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(Map.of("success", false, "message", "Erro ao gerar Excel"));
                }

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("success", false, "message", "Erro interno: " + e.getMessage()));
            }
        }

        @PostMapping("/balance/{mes}/{ano}")
        public ResponseEntity<?> generateBalanceExcel(
                @PathVariable Integer mes,
                @PathVariable Integer ano,
                @RequestParam(defaultValue = "Balanco") String tag) {

            try {
                Map<String, Object> requestData = new HashMap<>();
                requestData.put("tag", tag);
                requestData.put("month", mes);
                requestData.put("year", ano);
                requestData.put("tipo", "balance");
                // requestData.put("data", list);

                String urlPython = "http://localhost:5000/api/receber-lista";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                ObjectMapper mapper = new ObjectMapper();
                String jsonBody = mapper.writeValueAsString(requestData);

                HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
                ResponseEntity<Map> response = restTemplate.postForEntity(urlPython, request, Map.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    return ResponseEntity.ok().body(response.getBody());
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(Map.of("success", false, "message", "Erro ao gerar Excel"));
                }

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("success", false, "message", "Erro interno: " + e.getMessage()));
            }
        }
    }


