package com.weather.WeatherPlus.service;

import com.weather.WeatherPlus.config.BotConfig;
import com.weather.WeatherPlus.getters.GetterAdvancedWeather;
import com.weather.WeatherPlus.getters.GetterBaseWeather;
import com.weather.WeatherPlus.units.StoreUnits;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    StoreUnits storeUnits;

    public TelegramBot(BotConfig config) {

        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "get a welcome message"));
        listOfCommands.add(new BotCommand("/weather_base", "get a base weather"));
        listOfCommands.add(new BotCommand("/weather_advanced", "get a advanced weather"));
        listOfCommands.add(new BotCommand("/recomendation_of_clothers", "recomendation of clothers"));
        listOfCommands.add(new BotCommand("/update", "update information"));
        listOfCommands.add(new BotCommand("/change_utils", "change utils"));
        listOfCommands.add(new BotCommand("/help", "info how to use this bot"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e){
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();


            switch (messageText) {
                case "/start":{
                    startCommandReceive(chatId, update.getMessage().getChat().getFirstName());
                    break;

                }

                case "/weather_base": {
                    try {
                        weatherBaseCommandReceive(chatId);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }

                case "/weather_advanced": {
                    try {
                        weatherAdvancedCommandReceive(chatId);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }

                default: {
                    sendMessage(chatId, "Sorry, command was not recognized");
                }

            }
        }
    }

    private void startCommandReceive(long chatId, String firstName) {
        String answer = "Hi, " + firstName + ",  nice to meet you!";
        //log.info("Replied to user");
        sendMessage(chatId, answer);
    }


    private void weatherBaseCommandReceive(long chatId) throws IOException {
        GetterBaseWeather getterWeather = new GetterBaseWeather();
        String answer = getterWeather.getWeather();
        sendMessage(chatId, answer);
    }

    private void weatherAdvancedCommandReceive(long chatId) throws IOException {
        GetterAdvancedWeather getterWeather = new GetterAdvancedWeather();
        String answer = getterWeather.getWeather();
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        }catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }

    }

}
