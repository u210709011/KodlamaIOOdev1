package com.etiya.logging;

public class FileLogger extends BaseLogTarget {
    public FileLogger(LogLevel minLevel, boolean async) {
        super("file", minLevel, async, null);
    }

    @Override
    protected void write(LogEvent event) {
        String json = String.format(
                "{\"time\":\"%s\",\"level\":\"%s\",\"class\":\"%s\",\"thread\":\"%s\",\"correlationId\":\"%s\",\"message\":\"%s\"}",
                event.getTimestamp(),
                event.getLevel(),
                event.getClassName(),
                event.getThreadName(),
                event.getCorrelationId(),
                event.getMessage().replace("\"", "\\\"")
        );
        System.out.println("[FILE] " + json);
    }
}
