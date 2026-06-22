package com.etiya.logging;

public abstract class BaseLogTarget implements LogTarget {
    private final String name;
    private final LogLevel minLevel;
    private final boolean async;
    private final String fallbackTargetName;

    protected BaseLogTarget(String name, LogLevel minLevel, boolean async, String fallbackTargetName) {
        this.name = name;
        this.minLevel = minLevel;
        this.async = async;
        this.fallbackTargetName = fallbackTargetName;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean isAsync() {
        return async;
    }

    @Override
    public String fallbackTargetName() {
        return fallbackTargetName;
    }

    @Override
    public final void log(LogEvent event) throws Exception {
        if (!event.getLevel().isAtLeast(minLevel)) {
            return;
        }
        write(event);
    }

    protected abstract void write(LogEvent event) throws Exception;
}
