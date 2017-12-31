package com.dossier.checker.bots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;

/**
 * This components deals with registration of a bot to telegram platform
 */
@Component
public class TelegramBotRegister implements CommandLineRunner {

    private RomanianDossierCheckerBot romanianDossierCheckerBot;

    @Autowired
    public TelegramBotRegister(RomanianDossierCheckerBot romanianDossierCheckerBot) {
        this.romanianDossierCheckerBot = romanianDossierCheckerBot;
    }

    @Override
    public void run(String... args) throws Exception {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        botsApi.registerBot(romanianDossierCheckerBot);
    }
}

