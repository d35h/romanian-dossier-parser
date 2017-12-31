package com.dossier.checker.services;

import static java.util.Arrays.*;

import java.util.List;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.dossier.checker.services.common.RomanianDossierCheckerService;
import com.dossier.checker.services.handlers.SubjectHandler;
import com.dossier.checker.services.parsers.WebPageParser;

@RunWith(MockitoJUnitRunner.class)
public class RomanianDossierCheckerServiceTest {

    @Mock
    private SubjectHandler subjectHandler;

    @Mock
    private WebPageParser webPageParser;

    @InjectMocks
    private RomanianDossierCheckerService service;

    private List<String> pdfLinks = asList("link1", "link2");

    private static int ID = 1;

//    @Before
//    public void init() {
//        when(webPageParser.getPdfLinks()).thenReturn(pdfLinks);
//        when(subjectHandler.getDossierSubjectsByUri(any(URI.class))).thenReturn(getSubjects(ID));
//    }
//
//    @Test
//    public void testFindSubjectByDossierIdShouldReturnSubject() {
//        final Subject actual = service.findSubjectByDossierId("IdTest1");
//        final Subject expected = builder().firstName("FirstnameTest1").lastName("LastnameTest1").id("IdTest1").pdfUri("PdfTest1").build();
//        assertReflectionEquals(actual, expected);
//        verify(webPageParser).getPdfLinks();
//        verify(subjectHandler).getDossierSubjectsByUri(any(URI.class));
//        verifyNoMoreInteractions(webPageParser, subjectHandler);
//    }
//
//    @Test
//    public void testFindSubjectByDossierIdShouldNotReturnSubject() {
//        final Subject actual = service.("IdTest5");
//        assertNull(actual);
//        verify(webPageParser).getPdfLinks();
//        verify(subjectHandler, times(2)).getDossierSubjectsByUri(any(URI.class));
//        verifyNoMoreInteractions(webPageParser, subjectHandler);
//    }
//
//    private List<Subject> getSubjects(int id) {
//        return asList(
//                builder().firstName("FirstnameTest1").lastName("LastnameTest1").id("IdTest" + id).pdfUri("PdfTest1").build(),
//                builder().firstName("FirstnameTest2").lastName("LastnameTest2").id("IdTest" + ++id).pdfUri("PdfTest2").build(),
//                builder().firstName("FirstnameTest3").lastName("LastnameTest3").id("IdTest" + ++id).pdfUri("PdfTest3").build()
//        );
//    }
}
