package com.weather.WeatherPlus.parsers;

import com.weather.WeatherPlus.network.HttpClient;
import org.json.JSONObject;

import java.io.IOException;

public class ParserBaseWeather {
    public String parse() throws IOException {
        String output = HttpClient.getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=Novokuznetsk,ru&APPID=1ad07f1a062c4944c991c676c873d2c3");

        JSONObject jsonObject = new JSONObject(output);
        double temperature = jsonObject.getJSONObject("main").getDouble("temp");
        double windSpeed = jsonObject.getJSONObject("wind").getDouble("speed");
        double pressure = jsonObject.getJSONObject("main").getDouble("pressure");
        String city = jsonObject.getString("name");
        return "Weather in " + city +
                ":\n Temperature: " + temperature +
                ",\n Wind speed: " + windSpeed +
                ",\n Pressure: " + pressure;
    }
}
