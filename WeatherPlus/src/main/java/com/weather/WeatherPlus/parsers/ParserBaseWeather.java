package com.weather.WeatherPlus.parsers;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class ParserBaseWeather {
    public String parse() throws IOException {
        String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=Novokuznetsk,ru&APPID=1ad07f1a062c4944c991c676c873d2c3");

        JSONObject jsonObject = new JSONObject(output);
        double temperature = jsonObject.getJSONObject("main").getDouble("temp");
        double windSpeed = jsonObject.getJSONObject("wind").getDouble("speed");
        double pressure = jsonObject.getJSONObject("main").getDouble("pressure");
        String city = jsonObject.getString("name");
        return  "WEATHER IN " + city +
                ",\n TEMPERATURE - " + temperature +
                ",\n WIND SPEED - " + windSpeed +
                ",\n PRESSURE - " + pressure;
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
