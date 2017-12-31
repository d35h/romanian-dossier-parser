package com.dossier.checker.bots;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.dossier.checker.domain.Subject;
import com.dossier.checker.services.common.RomanianDossierCheckerService;
import com.dossier.checker.services.data.SubjectService;
import com.dossier.checker.utils.MessageUtils;

@Service
public class RomanianDossierCheckerBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(RomanianDossierCheckerBot.class);

    private final String pdfTelegramBotName;

    private final String pdfTelegramBotToken;

    private final RomanianDossierCheckerService romanianDossierCheckerService;

    private final SubjectService subjectService;

    @Autowired
    public RomanianDossierCheckerBot(@Value("${settings.bot.name}") String pdfTelegramBotName,
                                     @Value("${settings.bot.token}") String pdfTelegramBotToken,
                                     RomanianDossierCheckerService romanianDossierCheckerService,
                                     SubjectService subjectService) {
        this.pdfTelegramBotName = pdfTelegramBotName;
        this.pdfTelegramBotToken = pdfTelegramBotToken;
        this.romanianDossierCheckerService = romanianDossierCheckerService;
        this.subjectService = subjectService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (MessageUtils.hasChatIdAndMessage(update)) {
            executeOnUpdate(update);
        }
    }

    @Override
    public String getBotUsername() {
        return pdfTelegramBotName;
    }

    @Override
    public String getBotToken() {
        return pdfTelegramBotToken;
    }

    private void executeOnUpdate(Update update) {
        handleRequests(update);
    }

    private void handleRequests(Update update) {
        switch(update.getMessage().getText()) {
            case MessageUtils.START_COMMAND:
                sendMessageBack(MessageUtils.prepareMessage(update.getMessage().getChatId(), MessageUtils.WELCOMING_MESSAGE));
                break;
            default:
                sendMessageBack(MessageUtils.prepareMessage(update.getMessage().getChatId(), MessageUtils.generateLookupMessage(update.getMessage().getText())));
                findSubjectByDossierIdAndSendMessage(update);
                break;
        }
    }

    private Subject findSubjectByDossierId(String dossierId) {
        return subjectService.findById(dossierId);
    }

    private void findSubjectByDossierIdAndSendMessage(Update update) {
        Subject subject = findSubjectByDossierId(update.getMessage().getText());
        LOGGER.info("Found subject by id {}: {}", update.getMessage().getText(), subject);
        sendMessageBack(MessageUtils.prepareMessage(update.getMessage().getChatId(), MessageUtils.getResponseMessage(subject)));
    }

    private void sendMessageBack(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            LOGGER.debug("Error during sending message: {}", e);
        }
    }

}
