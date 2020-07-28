package com.github.mary296;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.LinkedHashMap;
import java.util.Map;

public class WeatherParser {
    public static Map<String, String> getWeatherData() {
        String siteUrl = "https://pogoda.ngs.ru/";

        Document doc;
        try {
            doc = Jsoup.connect(siteUrl).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Element weatherDiv = doc.selectFirst("div.pgd-short-card__content-table-row");
        System.out.println(weatherDiv.attr("class"));

        Map<String, String> weatherData = new LinkedHashMap<>();
        for (Element itemDiv : weatherDiv.children()) {
            String timeOfDay = itemDiv.child(0).text();
            String temperature = itemDiv.child(2).text();
            weatherData.put(timeOfDay, temperature);
        }
        return weatherData;
    }

    public static void main(String[] args) {
        System.out.println(getWeatherData());
    }
}
