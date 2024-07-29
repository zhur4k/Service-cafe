package com.yulkost.service.config;

import com.yulkost.service.bot.YulkostTelegramBot;
import com.yulkost.service.bot.ZamokTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotInitializer {
    private final YulkostTelegramBot yulkostTelegramBot;
    private final ZamokTelegramBot zamokTelegramBot;

    @Autowired
    public BotInitializer(YulkostTelegramBot yulkostTelegramBot, ZamokTelegramBot zamokTelegramBot) {
        this.yulkostTelegramBot = yulkostTelegramBot;
        this.zamokTelegramBot = zamokTelegramBot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(yulkostTelegramBot);
            telegramBotsApi.registerBot(zamokTelegramBot);
        } catch (TelegramApiException e) {
            // Handle exception
        }
    }
}
