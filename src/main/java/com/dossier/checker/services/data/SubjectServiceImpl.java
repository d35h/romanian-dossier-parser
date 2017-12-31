package com.dossier.checker.services.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dossier.checker.domain.Subject;
import com.dossier.checker.services.RomanianDossierCheckerService;

@Repository
public class SubjectServiceImpl implements SubjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RomanianDossierCheckerService.class);

    private SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Subject findById(String subjectId) {
        return subjectRepository.findById(subjectId);
    }

    @Override
    public List<Subject> findByLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            LOGGER.info("Unable to get subject by last name, since last name is not specified");
            return null;
        }

        LOGGER.info("Looking for subject with the name {}", lastName);
        return subjectRepository.findByLastNameIgnoreCase(lastName);
    }

    @Override
    public List<Subject> findByFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            LOGGER.info("Unable to get subject by first name, since first name is not specified");
            return null;
        }

        LOGGER.info("Looking for subject with the first name {}", firstName);
        return subjectRepository.findByFirstNameIgnoreCase(firstName);
    }

    @Override
    public Subject save(Subject subject) {
        if (subject == null) {
            LOGGER.info("Unable to save subject, subject object is null");
            return null;
        }

        LOGGER.info("Saving subject with the last name {}", subject.getLastName());
        return subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public List<Subject> saveAll(List<Subject> subjects) {
        if (subjects == null || subjects.isEmpty()) {
            LOGGER.info("Unable to save subject, subject object is null or empty");
            return null;
        }

        LOGGER.info("Saving subjects {}", subjects);
        return subjectRepository.save(subjects);
    }
}
