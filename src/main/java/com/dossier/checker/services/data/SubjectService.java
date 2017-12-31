package com.dossier.checker.services.data;

import java.util.List;

import com.dossier.checker.domain.Subject;

/**
 * Interface to avoid cyclic cycling injection
 */
public interface SubjectService {

    /**
     * Looks for subject entity by a specified subject id.
     *
     * @param subjectId an subject ID look to by.
     * @return subject which was found by a specified ID.
     */
    Subject findById(String subjectId);

    /**
     * Looks for subject entity by a specified subject last name, case sensitive.
     *
     * @param lastName a subject last name to look by.
     * @return list of subjects which were found by a specified subject last name.
     */
    List<Subject> findByLastName(String lastName);

    /**
     * Looks for subject entity by a specified subject first name, case sensitive.
     *
     * @param firstName a subject first name to look by.
     * @return list of subjects which were found by a specified subject last name.
     */
    List<Subject> findByFirstName(String firstName);

    /**
     * Persists a subject entity to the database and returns saved entity.
     *
     * @param subject a Subject to persist.
     * @return persisted subject.
     */
    Subject save(Subject subject);

    /**
     * Persists a subject entities to the database and returns all saved subjects.
     *
     * @param subjects to persist.
     * @return list of subjects which were saved
     */
    List<Subject> saveAll(List<Subject> subjects);
}
