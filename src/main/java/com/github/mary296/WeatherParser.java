package com.github.mary296;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.Map;

public class WeatherParser {
    public static String getWeatherData() {
        String siteUrl = "https://pogoda.ngs.ru/";

        Document doc;
        try {
            doc = Jsoup.connect(siteUrl).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Element weatherDiv = doc.selectFirst("div.pgd-short-card__content-table-row");

        StringBuilder weatherData = new StringBuilder();
        for (Element itemDiv : weatherDiv.children()) {
            if(weatherData.length() > 0) {
                weatherData.append('\n');
                weatherData.append('\n');
            }
            String timeOfDay = itemDiv.child(0).text();
            weatherData.append(Character.toUpperCase(timeOfDay.charAt(0)) + timeOfDay.substring(1));
            weatherData.append(' ');
            String temperature = itemDiv.child(2).text();
            weatherData.append(temperature);
            weatherData.append('\n');
            String weatherTerms = itemDiv.child(1).child(0).attr("title");
            weatherData.append(weatherTerms);
            weatherData.append('\n');
            String wind = itemDiv.child(3).text();
            weatherData.append("Ветер " + wind);
        }
        return weatherData.toString();
    }


    public static void main(String[] args) {
        System.out.println(getWeatherData());
    }
}
