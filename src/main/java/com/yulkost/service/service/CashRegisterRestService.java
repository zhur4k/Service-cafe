package com.yulkost.service.service;

import com.yulkost.service.model.Collection;
import com.yulkost.service.model.OrderItems;
import com.yulkost.service.model.Orders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CashRegisterRestService {

    private final String CASH_REGISTER_ID ="http://169.254.35.154" ;
    public Boolean sendFCheck(Orders order) {
        StringBuilder json = new StringBuilder("{\"F\":[" +
                "{\"C\":{\"cm\":\"Кассир:" + (order.getShift().getUsers().get(0)).getName() + "\"}}");
        for (OrderItems orderItem : order.getOrderItems()) {
            json.append(",{\"S\":{\"code\":").
                    append(orderItem.getItems().getCode()).

                    append(",").append("\"name\":\"").
                    append(orderItem.getItems().getNameOfItems()).
                    append(" ").
                    append(orderItem.getItems().getUnitPriceToPage()).
                    append(" ").
                    append(orderItem.getItems().getUnit().getName()).

                    append("\",").append("\"qty\":").
                    append(orderItem.getQuantity()).

                    append(",").append("\"price\":").
                    append(orderItem.getItems().getPriceToPage()).
                    append("}}");
//            if (orderItem.getDiscount() > 0) {
//                json.append(",{\"D\":{\"prc\":\"-").
//                        append(orderItem.getDiscount()).
//                        append("\"}}");
//            }
        }
        if (order.getCashPaid()>0) {

            json.append(",{\"P\":{").
                    append("\"no\":1,").
                    append("\"sum\":").
                    append(order.getCashPaidToPage()).
                    append("}}");
        }
            if (order.getCashLessPaid()>0){
                json.append(",{\"P\":{").
                        append("\"no\":2,").
                        append("\"sum\":").
                        append(order.getCashLessPaidToPage()).
                        append("}}");
            }
        json.append("]}");

            System.out.println(json+"\n\n\n\n");

        return sendPost(json.toString(), "/cgi/chk");
//            return true;
    }
    public Boolean sendIOCheck(Collection collection){
        StringBuilder json = new StringBuilder("{\"IO\":[" +
                "{\"C\":{\"cm\":\"Кассир:"+(collection.getShift().getUsers().get(0)).getName()+"\"}}");
        json.append(",{\"IO\":{\"sum\":");
        if(!collection.getTypeOfOperation()){
            json.append("-");
        }
        json.append(((float) collection.getSumOfOperation()) / 100).append("}}");
        json.append("]}");
        System.out.println(json);
        return sendPost(json.toString(), "/cgi/chk");
//        return true;
    }
    private Boolean sendPost(String json, String url) {
        try {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Создаем HttpEntity с JSON-чеком и заголовками
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        // Отправляем POST-запрос на сервер
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(CASH_REGISTER_ID + url, request, String.class);
        System.out.println("Ответ от сервера: " + response.getBody());
        return true;
        }catch (RuntimeException e){
            return false;
        }
    }
    private Boolean sendGet(String url) {
        try {
            // Отправляем GET-запрос на сервер
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(CASH_REGISTER_ID + url, String.class);

            // Обрабатываем ответ (если нужно)
            System.out.println("Ответ от сервера: " + response.getBody());
            return true;
        }catch (RuntimeException e){
            return false;
        }

    }
    public  boolean sendXReport() {
        System.out.println("Send X-report");
//        return true;
        return sendGet("/cgi/proc/printreport?10");
    }
    public boolean sendZReport() {
        System.out.println("Send Z-report");
//        return true;
        return sendGet("/cgi/proc/printreport?0");
    }
}
