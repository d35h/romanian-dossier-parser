package com.dossier.checker.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.dossier.checker.domain.Subject;

public class SubjectUtils {

    private final static int INDEX_OF_SUBJECT_LASTNAME = 0;

    private final static int INDEX_OF_SUBJECT_FIRSTNAME = 1;

    private final static int INDEX_OF_SUBJECT_ID = 2;

    private final static int MAX_CORRECT_NUMBER_OF_VALUES = 3;

    private final static String REGEX_MATCHER_FOR_DOSSIER_LAST_FIRST_NAMES = "[\\W|\\w]+\\s+[\\W|\\w]+";

    private final static String REGEX_MATCHER_FOR_DOSSIER_ID = "\\(\\d+\\/\\d+\\)\\s*$";

    public final static String REGEX_MATCHER_FOR_DOSSIER = REGEX_MATCHER_FOR_DOSSIER_LAST_FIRST_NAMES + REGEX_MATCHER_FOR_DOSSIER_ID;

    private final static String REGEX_REPLACE_PARENTHESES = "\\(|\\)";

    private final static String REGEX_LEADING_AND_TRAILING_WHITESPACES_AND_COMMA = "^[\\s]+|\\s+$|\\,";

    private final static String REGEX_REPLACE_DASH = "\\s{0,1}\\-\\s{0,1}";

    private final static String SPLITTER_FOR_SUBJECT = "\\s{1,}|\\(";

    public static Subject getSubjectFromString(String candidate) {
        final List<String> listWithSubjectValues = getListWithSubjectValues(candidate);
        correctCorruptedSubjectId(listWithSubjectValues);

        return Subject.builder()
                .lastName(listWithSubjectValues.get(INDEX_OF_SUBJECT_LASTNAME))
                .firstName(listWithSubjectValues.get(INDEX_OF_SUBJECT_FIRSTNAME))
                .id(listWithSubjectValues.get(INDEX_OF_SUBJECT_ID))
                .build();
    }

    /**
     * This method checks if subjectId has corrupted values and fixes this corruption.
     *
     * This is workaround, since the ministry of Romanian justition has not
     * consistent way of registering people with multiple first or last name.
     *
     * <strong>I do not really enjoy writing this piece of code!</strong>
     *
     * @param subjectValues represents values of subject, in more detail:
     *                      subjectValues[0] - last name
     *                      subjectValues[1] - first name
     *                      subjectValues[2] - subject id
     */
    private static void correctCorruptedSubjectId(List<String> subjectValues) {
        String subjectId = subjectValues.get(INDEX_OF_SUBJECT_ID);

        if (isSubjectIdCorrupted(subjectId)) {
            moveCorruptedValues(subjectValues);
        }
    }

    private static void moveCorruptedValues(List<String> subjectValues) {
        final String whitespace = "\\s{1,2}";
        final int splitterLimit = 3;
        String[] splittedSubjectId = subjectValues.get(INDEX_OF_SUBJECT_ID).split(whitespace, splitterLimit);

        int currentIndex = INDEX_OF_SUBJECT_ID;
        for (int i = splittedSubjectId.length - 1; i > -1; i--) {
            if (currentIndex == INDEX_OF_SUBJECT_ID) {
                subjectValues.set(currentIndex, splittedSubjectId[i]);
            } else {
                subjectValues.set(currentIndex, subjectValues.get(currentIndex) + StringUtils.SPACE + splittedSubjectId[i]);
            }
            currentIndex--;
        }
    }

    private static boolean isSubjectIdCorrupted(String subjectId) {
        final String regExDossierIdOnly = "^[\\d+]+\\/[\\d+]+$";
        return !subjectId.matches(regExDossierIdOnly);
    }

    private static List<String> getListWithSubjectValues(String subjectString) {
        subjectString = replaceAllLeadingTrailingSpacesFixDashFormatAndDeleteCommas(subjectString);
        return Lists.newArrayList(subjectString.split(SPLITTER_FOR_SUBJECT, MAX_CORRECT_NUMBER_OF_VALUES))
                .stream()
                .map(SubjectUtils::replaceAllParentheses)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static String replaceAllLeadingTrailingSpacesFixDashFormatAndDeleteCommas(String stringToReplace) {
        final String dash = "-";
        return stringToReplace.replaceAll(REGEX_LEADING_AND_TRAILING_WHITESPACES_AND_COMMA, StringUtils.EMPTY).replaceAll(REGEX_REPLACE_DASH, dash);
    }

    public static boolean isStringMatched(String candidate, String regEx) {
        return Pattern.compile(regEx).matcher(candidate).matches();
    }

    private static String replaceAllParentheses(String source) {
        return replaceAll(source, REGEX_REPLACE_PARENTHESES, StringUtils.EMPTY);
    }

    private static String replaceAll(String source, String regEx, String replaceBy) {
        return source.replaceAll(regEx, replaceBy);
    }
}
