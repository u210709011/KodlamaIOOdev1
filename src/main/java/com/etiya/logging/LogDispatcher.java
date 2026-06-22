package com.etiya.logging;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LogDispatcher {
    private final Map<String, List<String>> routes;
    private final Map<String, LogTarget> targetsByName;
    private final ExecutorService asyncPool;

    public LogDispatcher(Map<String, List<String>> routes, Map<String, LogTarget> targetsByName) {
        this.routes = routes;
        this.targetsByName = targetsByName;
        this.asyncPool = Executors.newFixedThreadPool(2);
    }

    public void dispatch(String operationKey, LogEvent event) {
        List<String> targetNames = routes.getOrDefault(operationKey, List.of("file"));
        for (String targetName : targetNames) {
            LogTarget target = targetsByName.get(targetName);
            if (target == null) {
                continue;
            }

            if (target.isAsync()) {
                asyncPool.submit(() -> safeWrite(target, event));
            } else {
                safeWrite(target, event);
            }
        }
    }

    private void safeWrite(LogTarget target, LogEvent event) {
        try {
            target.log(event);
        } catch (Exception targetError) {
            String fallbackName = target.fallbackTargetName();
            if (fallbackName != null && !fallbackName.isBlank()) {
                LogTarget fallbackTarget = targetsByName.get(fallbackName);
                if (fallbackTarget != null) {
                    try {
                        fallbackTarget.log(event.withMessage(event.getMessage() + " | fallbackReason=" + targetError.getMessage()));
                    } catch (Exception ignored) {
                        // Logging failures are intentionally swallowed to protect business flow.
                    }
                }
            }
        }
    }

    public void shutdown() {
        asyncPool.shutdown();
        try {
            if (!asyncPool.awaitTermination(5, TimeUnit.SECONDS)) {
                asyncPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            asyncPool.shutdownNow();
        }
    }
}
