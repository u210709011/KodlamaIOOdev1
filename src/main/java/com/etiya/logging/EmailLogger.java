package com.etiya.logging;

public class EmailLogger extends BaseLogTarget {
    public EmailLogger(LogLevel minLevel, boolean async) {
        super("email", minLevel, async, null);
    }

    @Override
    protected void write(LogEvent event) throws InterruptedException {
        // Simulate a slow email provider.
        Thread.sleep(2000);

        String html = "<html><body>"
                + "<h3>Log Alert</h3>"
                + "<p><b>Time:</b> " + event.getTimestamp() + "</p>"
                + "<p><b>Level:</b> " + event.getLevel() + "</p>"
                + "<p><b>Class:</b> " + event.getClassName() + "</p>"
                + "<p><b>Thread:</b> " + event.getThreadName() + "</p>"
                + "<p><b>CorrelationId:</b> " + event.getCorrelationId() + "</p>"
                + "<p><b>Message:</b> " + event.getMessage() + "</p>"
                + "</body></html>";

        System.out.println("[EMAIL] " + html);
    }
}
