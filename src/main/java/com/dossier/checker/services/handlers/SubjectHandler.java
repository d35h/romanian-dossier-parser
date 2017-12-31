package com.dossier.checker.services.handlers;

import java.net.URI;
import java.util.List;

import com.dossier.checker.domain.Subject;

public interface SubjectHandler {
    List<Subject> getDossierSubjectsByUri(URI pdfUri);
}
