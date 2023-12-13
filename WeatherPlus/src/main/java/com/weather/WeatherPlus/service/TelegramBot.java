package com.weather.WeatherPlus.service;

import com.weather.WeatherPlus.config.BotConfig;
import com.weather.WeatherPlus.getters.GetterAdvancedWeather;
import com.weather.WeatherPlus.getters.GetterBaseWeather;
import com.weather.WeatherPlus.getters.GetterRecommendationWeather;
import com.weather.WeatherPlus.units.StoreUnits;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
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
        listOfCommands.add(new BotCommand("/recommendation_of_clothes", "recommendation of clothes"));
        listOfCommands.add(new BotCommand("/update", "update information"));
        listOfCommands.add(new BotCommand("/change_utils", "change utils"));
        listOfCommands.add(new BotCommand("/help", "info how to use this bot"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();


            switch (messageText) {
                case "/start": {
                    startCommandReceive(chatId, update.getMessage().getChat().getFirstName());
                    break;

                }

                case "Base weather":
                case "/weather_base": {
                    try {
                        weatherBaseCommandReceive(chatId);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }

                case "Advanced weather":
                case "/weather_advanced": {
                    try {
                        weatherAdvancedCommandReceive(chatId);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }

                case "Cloth recommendations":
                case "/recommendation_of_clothes": {
                    try {
                        recommendationCommandReceive(chatId);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }

                case "Help":
                case "/help": {
                    sendMessage(chatId, HELP_TEXT);
                    break;
                }

                case "Update weather": {
                    sendMessage(chatId, "Sorry, command was not recognized");
                    break;
                }

                case "Change units": {
                    sendMessage(chatId, "Sorry, command was not recognized ");
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

    private void recommendationCommandReceive(long chatId) throws IOException {
        GetterRecommendationWeather getterRecommendation = new GetterRecommendationWeather();
        String answer = getterRecommendation.getRecommendation();
        sendMessage(chatId, answer);
    }

    private static final String HELP_TEXT = "This bot is designed to provide weather conditions in your city." +
            "\nYou can use one of the following commands by typing it or using the main menu:" +
            "\n\nType /start to see the welcome message" +
            "\n\nType /weather_base to get basic information about the current weather in your city" +
            "\n\nType /weather_advanced to get more detailed information about the current weather in your city" +
            "\n\nType /recommendation_of_clothes to see clothing recommendations depending on the weather in your city at the moment" +
            "\n\nType /update to update the weather information in your city" +
            "\n\nType /change_utils to change the units of temperature, pressure and wind speed" +
            "\n\nType /help to see this message again";

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = getKeyboardMarkup();
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }

    }

    private static ReplyKeyboardMarkup getKeyboardMarkup() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Help");
        row.add("Base weather");
        row.add("Advanced weather");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("Cloth recommendations");
        row.add("Update weather");
        row.add("Change units");
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

}
