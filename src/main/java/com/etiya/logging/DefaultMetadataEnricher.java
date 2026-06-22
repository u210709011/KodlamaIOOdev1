package com.etiya.logging;

import java.time.Instant;

public class DefaultMetadataEnricher implements LogEnricher {
    @Override
    public LogEvent apply(LogEvent event, String sourceName) {
        LogEvent result = event;
        if (result.getTimestamp() == null) {
            result = result.withTimestamp(Instant.now());
        }
        if (result.getClassName() == null) {
            result = result.withClassName(sourceName);
        }
        if (result.getThreadName() == null) {
            result = result.withThreadName(Thread.currentThread().getName());
        }
        if (result.getCorrelationId() == null) {
            result = result.withCorrelationId(LogContext.getCorrelationId());
        }
        return result;
    }
}
