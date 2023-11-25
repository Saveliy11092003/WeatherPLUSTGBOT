package com.weather.WeatherPlus.getters;

import com.weather.WeatherPlus.parsers.ParserBaseWeather;

import java.io.IOException;

import static com.weather.WeatherPlus.parsers.ParserBaseWeather.getUrlContent;

public class GetterBaseWeather {

    public String getWeather() throws IOException {
        ParserBaseWeather parser = new ParserBaseWeather();
        String message = parser.parse();
        return message;
    }

}
