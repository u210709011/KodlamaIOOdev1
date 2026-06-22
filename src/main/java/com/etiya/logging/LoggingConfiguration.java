package com.etiya.logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class LoggingConfiguration {
    private final Map<String, List<String>> routes = new HashMap<>();
    private final Map<String, LogLevel> minLevels = new HashMap<>();
    private final Map<String, Boolean> asyncFlags = new HashMap<>();
    private final Map<String, String> fallbackTargets = new HashMap<>();

    public static LoggingConfiguration loadFromResource(String resourceName) {
        LoggingConfiguration config = new LoggingConfiguration();
        Properties properties = new Properties();

        try (InputStream in = LoggingConfiguration.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (in == null) {
                throw new IllegalStateException("Config file not found: " + resourceName);
            }
            properties.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read config file: " + resourceName, e);
        }

        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key).trim();

            if (key.startsWith("route.")) {
                String operationKey = key.substring("route.".length());
                String[] items = value.split(",");
                List<String> targetList = new ArrayList<>();
                for (String item : items) {
                    targetList.add(item.trim());
                }
                config.routes.put(operationKey, targetList);
            } else if (key.startsWith("target.") && key.endsWith(".minLevel")) {
                String name = key.substring("target.".length(), key.length() - ".minLevel".length());
                config.minLevels.put(name, LogLevel.valueOf(value));
            } else if (key.startsWith("target.") && key.endsWith(".async")) {
                String name = key.substring("target.".length(), key.length() - ".async".length());
                config.asyncFlags.put(name, Boolean.parseBoolean(value));
            } else if (key.startsWith("target.") && key.endsWith(".fallback")) {
                String name = key.substring("target.".length(), key.length() - ".fallback".length());
                config.fallbackTargets.put(name, value);
            }
        }

        return config;
    }

    public Map<String, List<String>> getRoutes() {
        return routes;
    }

    public LogLevel minLevelOf(String targetName, LogLevel defaultValue) {
        return minLevels.getOrDefault(targetName, defaultValue);
    }

    public boolean asyncOf(String targetName, boolean defaultValue) {
        return asyncFlags.getOrDefault(targetName, defaultValue);
    }

    public String fallbackOf(String targetName) {
        return fallbackTargets.get(targetName);
    }
}
