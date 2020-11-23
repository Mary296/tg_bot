package com.github.mary296;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TableBot extends TelegramLongPollingBot {

    private int userId = 221040149;

    public TableBot() {
        LocalDateTime now = LocalDateTime.now();

        System.out.println(now);

        LocalDateTime sendTime = now.withHour(12).withMinute(10).withSecond(0);

        System.out.println(sendTime);

        Duration between = Duration.between(now, sendTime);

        System.out.println(between);
        System.out.println(between.getSeconds());

        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(() -> getDataAndSend(userId), between.getSeconds(), TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
    }

    public String getBotToken() {
        return "1340975056:AAHM2iuQdbWI-i3L7fl7lgWdQd4fIU7AGIs";
    }

    public void onUpdateReceived(Update update) {
        getDataAndSend(update.getMessage().getChatId());
    }

    private void getDataAndSend(long chatId) {
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
                .setChatId(chatId)
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
