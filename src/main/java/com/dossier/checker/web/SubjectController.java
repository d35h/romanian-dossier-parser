package com.dossier.checker.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dossier.checker.domain.Subject;
import com.dossier.checker.services.data.SubjectService;

@RestController
public class SubjectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectController.class);

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/rest/api/getSubjectByLastName/{lastName}")
    public ResponseEntity<List<Subject>> getSubjectByLastName(@PathVariable("lastName") String lastName) {
        LOGGER.info("Getting user firstName: " + lastName);
        return ResponseEntity.ok(subjectService.findByLastName(lastName));
    }


    @GetMapping("/rest/api/getSubjectByFirstName/{firstName}")
    public ResponseEntity<List<Subject>> getSubjectByFirstName(@PathVariable("firstName") String firstName) {
        LOGGER.info("Getting user firstName: " + firstName);
        return ResponseEntity.ok(subjectService.findByFirstName(firstName));
    }

    @PostMapping("/rest/api/getSubjectById")
    public ResponseEntity<Subject> getSubjectById(@RequestBody String subjectId) {
        LOGGER.info("Getting subject with id: " + subjectId);
        return ResponseEntity.ok(subjectService.findById(subjectId));
    }
}
