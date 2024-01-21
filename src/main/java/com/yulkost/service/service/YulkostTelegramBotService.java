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

    public void SendOrderToUser(Orders order){
        StringBuilder orderInfo = new StringBuilder();
        orderInfo.append("Заказ № ").append(order.getId()).append("\n");
        orderInfo.append("Стол № ").append(order.getNumberOfTable()).append("\n");
        for (OrderItems item :order.getOrderItems()) {
            orderInfo.append(item.getItems().getNameOfItems())
                    .append(" - ").append(item.getQuantity()*Float.parseFloat(item.getItems().getUnitPriceToPage()))
                    .append(" ")
                    .append(item.getItems()
                            .getUnit().getName())
                    .append("\n");
        }
        for (User user : order.getShift().getUsers())
            telegramBot.sendMessage(user.getChatId(),orderInfo.toString());
    }

}
