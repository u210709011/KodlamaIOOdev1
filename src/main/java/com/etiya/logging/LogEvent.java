package com.etiya.logging;

import java.time.Instant;

public class LogEvent {
    private final Instant timestamp;
    private final String className;
    private final String threadName;
    private final String correlationId;
    private final LogLevel level;
    private final String message;

    public LogEvent(
            Instant timestamp,
            String className,
            String threadName,
            String correlationId,
            LogLevel level,
            String message
    ) {
        this.timestamp = timestamp;
        this.className = className;
        this.threadName = threadName;
        this.correlationId = correlationId;
        this.level = level;
        this.message = message;
    }

    public static LogEvent empty(LogLevel level, String message) {
        return new LogEvent(null, null, null, null, level, message);
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getClassName() {
        return className;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public LogEvent withTimestamp(Instant value) {
        return new LogEvent(value, className, threadName, correlationId, level, message);
    }

    public LogEvent withClassName(String value) {
        return new LogEvent(timestamp, value, threadName, correlationId, level, message);
    }

    public LogEvent withThreadName(String value) {
        return new LogEvent(timestamp, className, value, correlationId, level, message);
    }

    public LogEvent withCorrelationId(String value) {
        return new LogEvent(timestamp, className, threadName, value, level, message);
    }

    public LogEvent withMessage(String value) {
        return new LogEvent(timestamp, className, threadName, correlationId, level, value);
    }
}
