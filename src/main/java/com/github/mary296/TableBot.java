package com.github.mary296;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

public class TableBot extends TelegramLongPollingBot {
    public String getBotToken() {
        return "1340975056:AAHM2iuQdbWI-i3L7fl7lgWdQd4fIU7AGIs";
    }

    public void onUpdateReceived(Update update) {

        Map<String, String> timeTable = TimeTableParser.getTodayTimeTable();
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("```");
        timeTable.forEach((time, lesson) -> {
            if (!lesson.isEmpty()) {
                messageBuilder.append(String.format("%5s", time));
                messageBuilder.append("  ");
                messageBuilder.append(lesson);
                messageBuilder.append("\n");
            }
        });
        messageBuilder.append('\n');
        messageBuilder.append(WeatherParser.getWeatherData());
        messageBuilder.append("```");

        String message = messageBuilder.toString();
        SendMessage command = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(update.getMessage().getChatId())
                .setParseMode("Markdown")
                .setText(message);
        try {
            execute(command); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return "tableforme_bot";
    }
}
