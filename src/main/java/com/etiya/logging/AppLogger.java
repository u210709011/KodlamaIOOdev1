package com.etiya.logging;

import java.util.List;

public class AppLogger {
    private final String sourceName;
    private final String operationKey;
    private final LogDispatcher dispatcher;
    private final List<LogEnricher> enrichers;

    public AppLogger(String sourceName, String operationKey, LogDispatcher dispatcher, List<LogEnricher> enrichers) {
        this.sourceName = sourceName;
        this.operationKey = operationKey;
        this.dispatcher = dispatcher;
        this.enrichers = enrichers;
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void log(LogLevel level, String message) {
        LogEvent event = LogEvent.empty(level, message);
        for (LogEnricher enricher : enrichers) {
            event = enricher.apply(event, sourceName);
        }
        dispatcher.dispatch(operationKey, event);
    }
}
