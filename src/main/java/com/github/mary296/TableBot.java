package com.github.mary296;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TableBot extends TelegramLongPollingBot {
    public String getBotToken() {
        return "1340975056:AAHM2iuQdbWI-i3L7fl7lgWdQd4fIU7AGIs";
    }

    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(update.getMessage().getChatId())
                .setText(update.getMessage().getText());
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return "tableforme_bot";
    }
}
