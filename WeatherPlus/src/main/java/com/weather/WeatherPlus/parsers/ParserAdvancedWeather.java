package com.weather.WeatherPlus.parsers;

import com.weather.WeatherPlus.network.HttpClient;
import org.json.JSONObject;

import java.io.IOException;

public class ParserAdvancedWeather {
    public String parse() throws IOException {
        String output = HttpClient.getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=Yakutsk,ru&APPID=1ad07f1a062c4944c991c676c873d2c3");

        JSONObject jsonObject = new JSONObject(output);
        double temperature = jsonObject.getJSONObject("main").getDouble("temp");
        double feels_like = jsonObject.getJSONObject("main").getDouble("feels_like");
        double temp_min = jsonObject.getJSONObject("main").getDouble("temp_min");
        double temp_max = jsonObject.getJSONObject("main").getDouble("temp_max");
        double humidity = jsonObject.getJSONObject("main").getDouble("humidity");
        double windSpeed = jsonObject.getJSONObject("wind").getDouble("speed");
        double pressure = jsonObject.getJSONObject("main").getDouble("pressure");
        double visibility = jsonObject.getDouble("visibility");
        String precipitation = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
        String city = jsonObject.getString("name");
        return  "Weather in " + city +
                ":\n Temperature: " + temperature +
                ",\n Feels like: " + feels_like +
                ",\n Wind speed: " + windSpeed +
                ",\n Pressure: " + pressure +
                ",\n Humidity: " + humidity +
                ",\n Visibility: " + visibility +
                ",\n Min temperature: " + temp_min +
                ",\n Max temperature: " + temp_max +
                ",\n Precipitation: " + precipitation;
    }
}
