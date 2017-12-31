package com.dossier.checker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "dossier_subjects")
public class Subject {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "pdf_uri")
    private String pdfUri;

    /* Id consists of an id of a dossier and a year when the dossier was submitted
     *
     *  Ex.: 85387/2016
     *  Where 85387 is id of dossier and 2016 is a year when the dossier was submitted
     * */
    @Id
    @Column(name = "dossier_id")
    private String id;
}
