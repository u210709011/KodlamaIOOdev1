package com.etiya.logging;

public interface LogEnricher {
    LogEvent apply(LogEvent event, String sourceName);
}
