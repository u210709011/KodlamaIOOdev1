package com.etiya.logging;

import java.util.regex.Pattern;

public class SensitiveDataMasker implements LogEnricher {
    private static final Pattern CARD_PATTERN = Pattern.compile("(card\\s*=\\s*)([0-9\\-]{12,25})", Pattern.CASE_INSENSITIVE);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("(password\\s*=\\s*)([^\\s,;]+)", Pattern.CASE_INSENSITIVE);

    @Override
    public LogEvent apply(LogEvent event, String sourceName) {
        String masked = event.getMessage();
        masked = CARD_PATTERN.matcher(masked).replaceAll("$1****");
        masked = PASSWORD_PATTERN.matcher(masked).replaceAll("$1****");
        return event.withMessage(masked);
    }
}
