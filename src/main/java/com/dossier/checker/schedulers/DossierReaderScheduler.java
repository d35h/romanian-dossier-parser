package com.dossier.checker.schedulers;

import static java.util.Arrays.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dossier.checker.utils.DateUtils;
import com.dossier.checker.services.common.RomanianDossierCheckerService;

@Component
public class DossierReaderScheduler {

    private static final String RO = "ro";

    private static final List<String> FORMATS = asList("dd.mm.yyyy");

    private Date dateOfTheLastProcessedFile;

    private final RomanianDossierCheckerService romanianDossierCheckerService;

    private final String webPageParserYear;

    @Autowired
    public DossierReaderScheduler(RomanianDossierCheckerService romanianDossierCheckerService,
                                  @Value("${settings.web.parser.year}") String webParserYear) {
        this.romanianDossierCheckerService = romanianDossierCheckerService;
        this.webPageParserYear = webParserYear;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(DossierReaderScheduler.class);

    @Scheduled(cron = "${seetings.dossier.reader.scheduler.cron}")
    public void readDossierDaily() throws ParseException {
        execute();
    }

    private void execute() throws ParseException {
        LOGGER.info("Dossier reader scheduler was executed");
        if (dateOfTheLastProcessedFile == null) {
            dateOfTheLastProcessedFile = getDateFromSpecifiedYear(webPageParserYear);
        }
        romanianDossierCheckerService.processAndSaveDossierSubjects(dateOfTheLastProcessedFile);
        dateOfTheLastProcessedFile = new Date();
    }

    private Date getDateFromSpecifiedYear(String year) throws ParseException {
        final String sourceDate = "01.01." + year;
        return DateUtils.tryToParse(RO, sourceDate, FORMATS);
    }
}
