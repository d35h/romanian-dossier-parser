package com.dossier.checker.utils;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import com.dossier.checker.domain.Subject;

/**
 * Created by Daniil.Zaru on 31/12/2017.
 */
public class MessageUtils {

    private static final String NOT_ON_THE_LIST_MESSAGE = "You are not on the list, but no worries, try later.";

    public static final String START_COMMAND = "/start";

    public static final String WELCOMING_MESSAGE = "Willkommen doamna sau domnule, \n\nI am here to check the state of your dossier." +
            "\n\nJust type your ID (dossier number: number/year, Example: 1763/2013) below:";

    public static SendMessage prepareMessage(long chatId, String messageToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messageToSend);

        return sendMessage;
    }

    public static boolean hasChatIdAndMessage(Update update) {
        return update.getMessage().getChatId() != null && update.getMessage().hasText();
    }

    public static String getResponseMessage(Subject foundSubjects) {
        return foundSubjects == null ? NOT_ON_THE_LIST_MESSAGE : generateSuccessfulLookupMessage(foundSubjects);
    }

    public static String generateLookupMessage(String lookupValue) {
        return "I am looking for a dossier by ID: " + lookupValue;
    }

    public static String generateSuccessfulLookupMessage(Subject foundSubjects) {
        return "We have got good news for you!\nYou are on the list!\nSee the details: \n" + foundSubjects + ".\nCool, huh?";
    }
}
