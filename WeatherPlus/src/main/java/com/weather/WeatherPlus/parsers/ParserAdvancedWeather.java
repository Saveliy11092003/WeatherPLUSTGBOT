package com.weather.WeatherPlus.parsers;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ParserAdvancedWeather {
    public String parse() throws IOException {
        String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=Novokuznetsk,ru&APPID=1ad07f1a062c4944c991c676c873d2c3");

        JSONObject jsonObject = new JSONObject(output);
        double temperature = jsonObject.getJSONObject("main").getDouble("temp");
        double feels_like = jsonObject.getJSONObject("main").getDouble("feels_like");
        double temp_min = jsonObject.getJSONObject("main").getDouble("temp_min");
        double temp_max = jsonObject.getJSONObject("main").getDouble("temp_max");
        double humidity = jsonObject.getJSONObject("main").getDouble("humidity");
        double windSpeed = jsonObject.getJSONObject("wind").getDouble("speed");
        double pressure = jsonObject.getJSONObject("main").getDouble("pressure");
        double visibility = jsonObject.getDouble("visibility");
        String city = jsonObject.getString("name");
        return  "WEATHER IN " + city +
                ",\n TEMPERATURE - " + temperature +
                ",\n FEELS LIKE - " + feels_like +
                ",\n WIND SPEED - " + windSpeed +
                ",\n PRESSURE - " + pressure +
                ",\n HUMIDITY - " + humidity +
                ",\n VISIBILITY - " + visibility +
                ",\n MIN TEMPERATURE - " + temp_min +
                ",\n MAX TEMPERATURE - " + temp_max;
    }


    public static String getUrlContent(String urlAdress) throws IOException {
        StringBuffer content = new StringBuffer();
        URL url = new URL(urlAdress);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
        String line;
        while((line = bufferedReader.readLine()) != null){
            content.append(line + "\n");
        }
        bufferedReader.close();
        return content.toString();
    }
}
