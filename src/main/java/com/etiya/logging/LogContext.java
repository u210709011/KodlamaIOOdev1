package com.etiya.logging;

import java.util.UUID;

public final class LogContext {
    private static final ThreadLocal<String> CORRELATION_ID = new ThreadLocal<>();

    private LogContext() {
    }

    public static void startRequest(String correlationId) {
        CORRELATION_ID.set(correlationId);
    }

    public static String getCorrelationId() {
        String value = CORRELATION_ID.get();
        if (value == null || value.isBlank()) {
            value = UUID.randomUUID().toString();
            CORRELATION_ID.set(value);
        }
        return value;
    }

    public static void clear() {
        CORRELATION_ID.remove();
    }
}
