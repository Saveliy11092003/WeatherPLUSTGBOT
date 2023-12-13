package com.weather.WeatherPlus.getters;

import com.weather.WeatherPlus.parsers.ParserAdvancedWeather;

import java.io.IOException;

public class GetterAdvancedWeather {
    public String getWeather() throws IOException {
        ParserAdvancedWeather parser = new ParserAdvancedWeather();
        String message = parser.parse();
        return message;
    }
}
