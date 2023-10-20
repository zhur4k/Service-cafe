package com.yulkost.service.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CheckController {

    @PostMapping("/sendCheck")
    public ResponseEntity<String> sendSalesCheck(@RequestBody String json) {
        // URL, на который будет отправлен запрос
        String url = "http://exale.com/cgi/chk";

        // Заголовки HTTP-запроса
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Создаем HttpEntity с JSON-чеком и заголовками
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        // Отправляем POST-запрос на сервер
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        System.out.println("4");
        return response;

    }
}