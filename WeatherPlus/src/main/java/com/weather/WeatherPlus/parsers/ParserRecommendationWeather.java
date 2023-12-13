package com.weather.WeatherPlus.parsers;

import com.weather.WeatherPlus.network.HttpClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONObject;

import java.io.IOException;

public class ParserRecommendationWeather {
    public RecommendationInfoDto parseRecommendationInfo() throws IOException {
        String output = HttpClient.getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=Yakutsk,ru&APPID=1ad07f1a062c4944c991c676c873d2c3");

        JSONObject jsonObject = new JSONObject(output);
        double temperature = jsonObject.getJSONObject("main").getDouble("temp");
        String precipitation = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");

        RecommendationInfoDto dto = new RecommendationInfoDto(temperature, precipitation);
        return dto;
    }

    @Getter
    @AllArgsConstructor
    public static class RecommendationInfoDto {
        private double temperature;
        private String precipitation;
    }
}