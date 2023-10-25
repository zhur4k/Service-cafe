package com.yulkost.service.service;

import com.yulkost.service.model.Orders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CashRegisterService {

    private final String CASH_REGISTER_ID ="http://169.254.35.154" ;
    public String SendFCheck(Orders order){
        String json = "{\"F\":[" +
                "{\"C\":{\"cm\":\"Кассир: Светлана\"}}" +
                ",{\"S\":{\"code\":\"1234567890121\",\"name\":\"Конфета\",\"qty\":\"1.000\"\"price\":\"0.01\",\"sum\":\"0.01\"}}" +
                ",{\"P\":{}}" +
                "]}";

        return SendJson(json, "/cgi/chk");
    }

    private String SendJson(String json,String url){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Создаем HttpEntity с JSON-чеком и заголовками
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        // Отправляем POST-запрос на сервер
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(CASH_REGISTER_ID +url, request, String.class);
        System.out.println("Ответ от сервера: " + response.getBody());
        return "Запрос успешно выполнен!";
    }
}
