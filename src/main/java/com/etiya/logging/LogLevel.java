package com.etiya.logging;

public enum LogLevel {
    DEBUG(10),
    INFO(20),
    WARN(30),
    ERROR(40);

    private final int weight;

    LogLevel(int weight) {
        this.weight = weight;
    }

    public boolean isAtLeast(LogLevel threshold) {
        return this.weight >= threshold.weight;
    }
}
