package com.db.service.interpreter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterParser {

    private static final Pattern TERM_PATTERN = Pattern.compile("(\\w+)\\s*=\\s*([^\\s&]+)", Pattern.CASE_INSENSITIVE);

    public static Expression parse(String filterString) {
        if (filterString == null || filterString.isBlank()) {
            return context -> true;
        }
        String normalized = filterString.trim();
        String[] andParts = normalized.split("\\s+[Aa][Nn][Dd]\\s+");
        Expression result = null;
        for (String part : andParts) {
            Expression term = parseTerm(part.trim());
            if (term == null) continue;
            result = result == null ? term : new AndExpression(result, term);
        }
        return result != null ? result : context -> true;
    }

    private static Expression parseTerm(String term) {
        Matcher m = TERM_PATTERN.matcher(term);
        if (m.find()) {
            return new TerminalExpression(m.group(1), m.group(2).trim());
        }
        return null;
    }
}
