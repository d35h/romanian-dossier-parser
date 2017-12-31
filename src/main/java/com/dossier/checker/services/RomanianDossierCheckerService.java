package com.dossier.checker.services;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dossier.checker.services.data.SubjectService;
import com.dossier.checker.services.handlers.SubjectHandler;
import com.dossier.checker.services.parsers.WebPageParser;

@Service
public class RomanianDossierCheckerService {

    private final SubjectHandler subjectHandler;

    private final SubjectService subjectService;

    private final WebPageParser webPageParser;

    @Autowired
    public RomanianDossierCheckerService(SubjectHandler subjectHandler,
                                         SubjectService subjectService,
                                         WebPageParser webPageParser) {
        this.subjectHandler = subjectHandler;
        this.subjectService = subjectService;
        this.webPageParser = webPageParser;
    }

    public void processAndSaveDossierSubjects(Date dateOfTheLastProcessedFile) {
        webPageParser.getPdfLinks()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().after(dateOfTheLastProcessedFile))
                .forEach(entry -> processAndSaveDossiers(entry.getValue()));
    }

    private void processAndSaveDossiers(List<String> linksToDossier) {
        linksToDossier.forEach(link -> subjectService.saveAll(subjectHandler.getDossierSubjectsByUri(URI.create(link)).stream().collect(Collectors.toList())));
    }
}
