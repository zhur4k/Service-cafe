package com.yulkost.service.controller;

import com.yulkost.service.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CashRegisterRestController {
    @PostMapping("/submitOrder")
    public String SubmitOrder(@RequestBody Order order)
    {
//        String jsonCheck = "{\"F\":[{\"C\":{\"cm\":\"Кассир: Светлана\"}},{\"S\":{\"code\":\"1\",\"price\":\"5\",\"name\":\"Конфета\"},{\"S\":{\"code\":\"2\",\"price\":\"15\",\"name\":\"Печенье\",\"qty\":\"0.5\"},{\"D\":{\"prc\":\"5\",\"all\":\"1\"},{\"P\":{}}]}";
//
//        // Создаем RestTemplate
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Отправляем JSON-чек на сервер
//        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/sendCheck", jsonCheck, String.class);
//
//        // Обрабатываем ответ, если это необходимо
//        String responseBody = response.getBody();
//        System.out.println("Ответ от сервера: " + responseBody);
        return "Заказ успешно сохранен!";
    }
}
