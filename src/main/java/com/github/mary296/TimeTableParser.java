package com.github.mary296;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;


public class TimeTableParser {
    public static Map<String, String> getTodayTimeTable() {
        String siteUrl = "https://table.nsu.ru/group/17133";

        Document doc;
        try {
            doc = Jsoup.connect(siteUrl).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Element table = doc.selectFirst("table.time-table");

        String today = getToday();

        int dayOfWeekIndex = getTodayTimeTableColumn(table, today);

        if(dayOfWeekIndex == -1) {
            return Collections.EMPTY_MAP;
        }

        Map<String, String> timeTable = new LinkedHashMap<>();

        table.child(1).children().stream().skip(1).forEach(element -> {
            String time = element.child(0).text();

            String lesson = element.child(dayOfWeekIndex).text();

            timeTable.put(time, lesson);
        });

        return timeTable;
    }

    private static String getToday() {
        return LocalDate.now()
                .getDayOfWeek()
                .getDisplayName(TextStyle.FULL, new Locale("ru", "RU"));
    }

    private static int getTodayTimeTableColumn(Element timeTable, String today) {
        int dayOfWeekIndex = 0;

        Elements daysRow = timeTable.child(1).child(0).children();

        for (Element element : daysRow) {
            if (element.text().equalsIgnoreCase(today)) {
                return dayOfWeekIndex;
            }

            dayOfWeekIndex++;
        }

       return -1;
    }
}
