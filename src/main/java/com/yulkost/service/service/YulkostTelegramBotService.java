package com.yulkost.service.service;


import com.yulkost.service.bot.YulkostTelegramBot;
import com.yulkost.service.model.OrderItems;
import com.yulkost.service.model.Orders;
import com.yulkost.service.model.User;
import org.springframework.stereotype.Service;

@Service
public class YulkostTelegramBotService {
    private YulkostTelegramBot telegramBot;

    public YulkostTelegramBotService(YulkostTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void SendOrderToUser(User user, Orders order){
        StringBuilder orderInfo = new StringBuilder();
        orderInfo.append("Заказ № ").append(order.getId()).append("\n");
        for (OrderItems item :order.getOrderItems()) {
            orderInfo.append(item.getItems().getNameOfItems()).append(" - ").append(item.getQuantity()).append(" ").append(item.getItems().getUnit().getName()).append("\n");
        }
        telegramBot.sendMessage(user.getChatId(),orderInfo.toString());
    }

}
