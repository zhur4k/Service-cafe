package com.yulkost.service.service;

import com.yulkost.service.dto.mapper.ErrorCodeMapper;
import com.yulkost.service.model.Collection;
import com.yulkost.service.model.OrderItems;
import com.yulkost.service.model.Orders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CashRegisterRestService {

    private final String CASH_REGISTER_ID ="http://169.254.35.154" ;
    private final String URL ="/cgi/chk" ;
    public void sendFCheck(Orders order,Long orderItemId) {
        StringBuilder json = new StringBuilder("{\"F\":[" +
                "{\"C\":{\"cm\":\"Кассир:" + (order.getShift().getUsers().get(0)).getName() + "\"}}");
        for (OrderItems orderItem : order.getOrderItems()) {
            orderItemId++;
            json.append(",{\"S\":{\"code\":").
                    append(orderItemId).

                    append(",").append("\"name\":\"").
                    append(orderItem.getNameOfItems()).
                    append(" ").
                    append(orderItem.getUnitPriceToPage()).
                    append(" ").
                    append(orderItem.getUnit()).

                    append("\",").append("\"qty\":").
                    append(orderItem.getQuantity()).

                    append(",").append("\"price\":").
                    append(orderItem.getPriceToPage()).
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
        sendPost(json.toString(), URL);

    }
    public void sendIOCheck(Collection collection){
        StringBuilder json = new StringBuilder("{\"IO\":[" +
                "{\"C\":{\"cm\":\"Кассир:"+(collection.getShift().getUsers().get(0)).getName()+"\"}}");
        json.append(",{\"IO\":{\"sum\":");
        if(!collection.getTypeOfOperation()){
            json.append("-");
        }
        json.append(((float) collection.getSumOfOperation()) / 100).append("}}");
        json.append("]}");
        System.out.println("sendIO");
        sendPost(json.toString(), URL);
    }
    private void sendPost(String json, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Создаем HttpEntity с JSON-чеком и заголовками
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        // Отправляем POST-запрос на сервер
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(CASH_REGISTER_ID + url, request, String.class);
        String errorCode = extractErrorCodeFromResponse(response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()){
                // Получаем описание ошибки по коду из карты ошибок
                String errorDescription = ErrorCodeMapper.getErrorDescription(errorCode);

                System.out.println("Код ошибки: " + errorCode);
                System.out.println("Описание ошибки: " + errorDescription);
                throw new RuntimeException(errorDescription);
        }
    }

    private void sendGet(String url) {
            // Отправляем GET-запрос на сервер
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(CASH_REGISTER_ID + url, String.class);

            // Обрабатываем ответ (если нужно)
            System.out.println("Ответ от сервера: " + response.getBody());
        // Получаем код ошибки из ответа сервера
        String errorCode = extractErrorCodeFromResponse(response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()){
            // Получаем описание ошибки по коду из карты ошибок
            String errorDescription = ErrorCodeMapper.getErrorDescription(errorCode);

            System.out.println("Код ошибки: " + errorCode);
            System.out.println("Описание ошибки: " + errorDescription);
            throw new RuntimeException(errorDescription);
        }
    }
    public  void sendXReport() {
        System.out.println("Send X-report");
        sendGet("/cgi/proc/printreport?10");
    }
    public void sendZReport() {
        System.out.println("Send Z-report");
        sendGet("/cgi/proc/printreport?0");
    }
    private String extractErrorCodeFromResponse(String responseBody) {
        String errorCodePattern = "\"e\":\"(\\w+)\"";
        Pattern pattern = Pattern.compile(errorCodePattern);
        Matcher matcher = pattern.matcher(responseBody);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            // В случае отсутствия совпадений, можно вернуть значение по умолчанию или null
            return null;
        }
    }
}
