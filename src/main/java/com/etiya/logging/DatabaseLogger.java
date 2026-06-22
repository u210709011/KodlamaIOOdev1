package com.etiya.logging;

public class DatabaseLogger extends BaseLogTarget {
    public DatabaseLogger(LogLevel minLevel, boolean async, String fallbackTargetName) {
        super("db", minLevel, async, fallbackTargetName);
    }

    @Override
    protected void write(LogEvent event) {
        // Demo purpose: simulate DB failure if message contains DB_DOWN.
        if (event.getMessage().contains("DB_DOWN")) {
            throw new RuntimeException("Database connection is down");
        }

        System.out.println("[DB] "
                + "time=" + event.getTimestamp()
                + ", level=" + event.getLevel()
                + ", class=" + event.getClassName()
                + ", thread=" + event.getThreadName()
                + ", correlationId=" + event.getCorrelationId()
                + ", message=" + event.getMessage());
    }
}
