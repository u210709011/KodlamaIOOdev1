package com.etiya.logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogManager {
    private final LogDispatcher dispatcher;
    private final List<LogEnricher> enrichers;

    public LogManager(LogDispatcher dispatcher, List<LogEnricher> enrichers) {
        this.dispatcher = dispatcher;
        this.enrichers = enrichers;
    }

    public static LogManager defaultSetup() {
        LoggingConfiguration config = LoggingConfiguration.loadFromResource("logging.properties");

        Map<String, LogTarget> targets = new HashMap<>();

        FileLogger fileLogger = new FileLogger(
                config.minLevelOf("file", LogLevel.DEBUG),
                config.asyncOf("file", false)
        );
        DatabaseLogger dbLogger = new DatabaseLogger(
                config.minLevelOf("db", LogLevel.WARN),
                config.asyncOf("db", false),
                config.fallbackOf("db")
        );
        EmailLogger emailLogger = new EmailLogger(
                config.minLevelOf("email", LogLevel.ERROR),
                config.asyncOf("email", true)
        );
        SlackAndDigestLogger slackAndDigestLogger = new SlackAndDigestLogger(
                config.minLevelOf("slackDigest", LogLevel.ERROR),
                config.asyncOf("slackDigest", true)
        );

        targets.put(fileLogger.name(), fileLogger);
        targets.put(dbLogger.name(), dbLogger);
        targets.put(emailLogger.name(), emailLogger);
        targets.put(slackAndDigestLogger.name(), slackAndDigestLogger);

        LogDispatcher dispatcher = new LogDispatcher(config.getRoutes(), targets);
        List<LogEnricher> enrichers = new ArrayList<>();
        enrichers.add(new DefaultMetadataEnricher());
        enrichers.add(new SensitiveDataMasker());
        return new LogManager(dispatcher, enrichers);
    }

    public AppLogger loggerFor(String operationKey) {
        return new AppLogger(operationKey, operationKey, dispatcher, enrichers);
    }

    public void shutdown() {
        dispatcher.shutdown();
    }
}
