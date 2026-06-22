package com.etiya.logging;

import java.util.concurrent.atomic.AtomicInteger;

public class SlackAndDigestLogger extends BaseLogTarget {
    private final AtomicInteger errorCount = new AtomicInteger(0);

    public SlackAndDigestLogger(LogLevel minLevel, boolean async) {
        super("slackDigest", minLevel, async, null);
    }

    @Override
    protected void write(LogEvent event) {
        // Part 1: send each accepted event to Slack channel (simulated).
        System.out.println("[SLACK] #" + event.getLevel() + " " + event.getMessage()
                + " (corr=" + event.getCorrelationId() + ")");

        // Part 2: every 100 ERROR logs, send one summary email (simulated).
        if (event.getLevel() == LogLevel.ERROR) {
            int current = errorCount.incrementAndGet();
            if (current % 100 == 0) {
                System.out.println("[EMAIL-SUMMARY] Last 100 ERROR logs reached. total=" + current);
            }
        }
    }
}
