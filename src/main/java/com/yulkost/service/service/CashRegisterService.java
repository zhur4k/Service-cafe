package com.yulkost.service.service;

import com.yulkost.service.model.OrderItems;
import com.yulkost.service.model.Orders;
import com.yulkost.service.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CashRegisterService {

    private final String CASH_REGISTER_ID ="http://169.254.35.154" ;
    public Boolean sendFCheck(Orders order){
        StringBuilder json = new StringBuilder("{\"F\":[" +
                "{\"C\":{\"cm\":\"Кассир:"+order.getShift().getUser().getName()+"\"}}");
        for (OrderItems orderItem :order.getOrderItems()) {
            json.append(",{\"S\":{\"code\":\"").append(orderItem.getItems().getCode()).append("\",\"name\":\"").append(orderItem.getItems().getNameOfItems()).append("\",").append("\"qty\":\"").append(orderItem.getQuantity()).append("\"\"price\":\"").append(orderItem.getItems().getPriceToPage()).append("\"}}");
        }
                json.append(",{\"P\":{}}");
        json.append("]}");

//        return sendPost(json.toString(), "/cgi/chk");\
        System.out.println(json);
        return true;
    }

    private Boolean sendPost(String json, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Создаем HttpEntity с JSON-чеком и заголовками
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        // Отправляем POST-запрос на сервер
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(CASH_REGISTER_ID + url, request, String.class);
        System.out.println("Ответ от сервера: " + response.getBody());
        return true;
    }
    private Boolean sendGet(String url) {
        // Отправляем GET-запрос на сервер
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(CASH_REGISTER_ID + url, String.class);

        // Обрабатываем ответ (если нужно)
        System.out.println("Ответ от сервера: " + response.getBody());
        return true;
    }
    public  boolean sendXReport() {
        System.out.println("Send X-report");
        return true;
//        return sendGet("/cgi/proc/printreport?10");
    }
    public boolean sendZReport() {
        System.out.println("Send Z-report");
        return true;
//        return sendGet("/cgi/proc/printreport?0");
    }
    public boolean sendChangeMoneyInCashRegister(User user,Integer sum){
        String jsonString =
                "{" +
                "\"IO\":[" +
                "{\"C\":{\"cm\":\""+user.getName()+"\"}}," +
                "{\"IO\":{\"sum\":\""+sum+"\"}}" +
                "]" +
                "}";
//        return sendPost(jsonString,"/cgi/chk");
        return true;
    }
}
