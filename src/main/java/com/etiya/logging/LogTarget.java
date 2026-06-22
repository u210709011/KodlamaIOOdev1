package com.etiya.logging;

public interface LogTarget {
    String name();

    void log(LogEvent event) throws Exception;

    boolean isAsync();

    String fallbackTargetName();
}
