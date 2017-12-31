package com.dossier.checker.services.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dossier.checker.domain.Subject;

public interface SubjectRepository extends JpaRepository<Subject, String> {
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
    List<Subject> findByLastNameIgnoreCase(String lastName);

    /**
     * Looks for subject entity by a specified subject first name, case sensitive.
     *
     * @param firstName a subject first name to look by.
     * @return list of subjects which were found by a specified subject last name.
     */
    List<Subject> findByFirstNameIgnoreCase(String firstName);
}
