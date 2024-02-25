package com.yulkost.service.bot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

@Slf4j
@Component
public class YulkostTelegramBot extends TelegramLongPollingBot{
    @Override
    public String getBotUsername() { return "yulkost_bot"; }
    @Override
    public String getBotToken() { return "6192725078:AAF6ZHs8lbRx-tMoAIRaicp80oK6rztXC9c"; }
    @Override
    public void onUpdateReceived(@NotNull Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String memberName = update.getMessage().getFrom().getFirstName();

            switch (messageText){
                case "/start":
                    startBot(chatId, memberName);
                    break;
                case "/id":
                    ChatIdBot(chatId, memberName);
                    break;
                case "/server":
                    sendMessage(chatId, getServerIP());
                    break;
                default: log.info("Unexpected message");
            }
        }
    }
    private void startBot(long chatId, String userName) {
        sendMessage(chatId,"Привет, " + userName + "! Если тебе нужен твой ChatId нажми на /id");
    }
    private void ChatIdBot(long chatId, String userName) {
        sendMessage(chatId,userName +" Ваш chatId:");
        sendMessage(chatId, Long.toString(chatId));
    }

    public void sendMessage(long chatId, String text){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }
    public static String getServerIP() {
        try {

        URL url = new URL("https://api64.ipify.org?format=json");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(connection.getInputStream());

        // Получаем значение поля "ip" из JSON
        String routerIP = rootNode.get("ip").asText();

        connection.disconnect();
            return "http://"+routerIP+":8081";

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
