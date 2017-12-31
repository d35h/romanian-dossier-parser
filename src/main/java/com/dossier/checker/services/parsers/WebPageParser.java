package com.dossier.checker.services.parsers;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dossier.checker.utils.DateUtils;

/**
 * This service parses the page of the romanian ministry of justice (link should be specified in properties) and returns
 * their lists, which are containing information about the actual state of a dosser.
 *
 * It should be added that there is no naming convention for the pdf files they have on their, this is why here can be found
 * quite massive and unreadable regExs.
 *
 * (Good point for the future to reconsider them and make it at least a little bit more readable)
 *
 * This parser is needed as the romanian ministry of justice refused to offer me open API to obtain their lists.
 */
@Service
public class WebPageParser {

    private final List<String> FORMATS = Arrays.asList("dd.MM.yyyy", "dd_MMMM_yyyy", "dd_MM_yyyy", "dd_.MM.yyyy", "dd._MM.yyyy");

    private static final Logger LOGGER = LoggerFactory.getLogger(WebPageParser.class);

    private static final String REGEX_DATE = "\\d{1,2}[\\.\\_]\\d{1,2}[\\.\\_]\\d{4}|\\d{1,2}\\_[A-Za-z]{2,}\\_\\d{4}|\\d{1,2}\\_\\.\\d{1,2}\\.\\d{4,}|\\d{1,2}\\.\\_\\d{1,2}\\.\\d{4,}";

    private final String ministryDossierUrl;

    private final String webParserYear;


    //This flag allows to resolve links using absolute path instead of relative
    private static final String A_TAG_RESOLVER = "abs:href";

    @Autowired
    public WebPageParser(@Value("${general.ministry.dossier.url}") String ministryDossierUrl,
                         @Value("${settings.web.parser.year}") String webParserYear) {
        this.ministryDossierUrl = ministryDossierUrl;
        this.webParserYear = webParserYear;
    }

    public Map<Date, List<String>> getPdfLinks() {
        final String regexPdfForSpecifiedYear = "a[href~=/images/.+" + webParserYear + ".*\\.pdf$]";
        try {
            LOGGER.info("Trying to get web page from the specified URL: {}", ministryDossierUrl);
            List<String> webLinks = getDocumentByUrl(ministryDossierUrl).select(regexPdfForSpecifiedYear).stream().map(link -> link.attr(A_TAG_RESOLVER)).collect(Collectors.toList());
            return webLinks.stream().collect(Collectors.groupingBy(
                    this::getDateFromURL,
                    Collectors.mapping(webLink -> webLink, Collectors.toList())
            ));
        } catch (IOException e) {
            LOGGER.info("Failed when trying to get web page from the specified URL {}: {}", ministryDossierUrl, e);
        }
        return new HashMap<>();
    }

    private Date getDateFromURL(String url) {
        final String romanianLocale = "ro";
        Matcher dateMatcher = Pattern.compile(REGEX_DATE).matcher(url);
        if (!dateMatcher.find()) {
            LOGGER.info("Unable to find date matcher for url: {}", url);
            return null;
        }

        return DateUtils.tryToParse(romanianLocale, dateMatcher.group(), FORMATS);
    }

    public Document getDocumentByUrl(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}
