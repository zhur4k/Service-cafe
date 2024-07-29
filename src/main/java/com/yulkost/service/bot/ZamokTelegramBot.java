package com.yulkost.service.bot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yulkost.service.service.MenuService;
import com.yulkost.service.service.MessageSplitterService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Slf4j
@Component
public class ZamokTelegramBot extends TelegramLongPollingBot{
    public ZamokTelegramBot(MenuService menuService, MessageSplitterService messageSplitterService) {
        this.menuService = menuService;
        this.messageSplitterService = messageSplitterService;
    }

    @Override
    public String getBotUsername() { return "zamok_14_bot"; }
    @Override
    public String getBotToken() { return "7278455018:AAEsOqTdqNHRFw22KeGnw0hZEMwqGOUMY3E"; }
    private final MenuService menuService;
    private final MessageSplitterService messageSplitterService;
    @Override
    public void onUpdateReceived(@NotNull Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String memberName = update.getMessage().getFrom().getFirstName();

            switch (messageText) {
                case "/start" -> startBot(chatId, memberName);
                case "/menu" -> getMenu(chatId);
                default -> log.info("Unexpected message");
            }
        }
    }
    private void startBot(long chatId, String userName) {
        sendMessage(chatId,"Привет! Если тебе нужен твой ChatId нажми на /menu");
    }
    private void getMenu(long chatId) {
        sendLongMessage(chatId, menuService.getStringMenu() + " " + getServerIP() + "/menu");
    }
    public void sendLongMessage(long chatId, String text) {
        List<String> messages = messageSplitterService.splitMessage(text);
        for (String message : messages) {
            sendMessage(chatId, message);
        }
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
