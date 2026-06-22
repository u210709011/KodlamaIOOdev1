package com.etiya.service;

import com.etiya.logging.AppLogger;
import com.etiya.logging.LogManager;

public class PaymentService {
    private final AppLogger logger;

    public PaymentService(LogManager logManager) {
        this.logger = logManager.loggerFor("PaymentService.processPayment");
    }

    public void processPayment(String cardNo, String password, double amount) {
        logger.info("Payment started card=" + cardNo + ", password=" + password + ", amount=" + amount);
        logger.warn("Payment risk check warning for card=" + cardNo);
        logger.error("Payment failed by bank for card=" + cardNo);
    }

    public void processPaymentWithDbIssue(String cardNo, String password, double amount) {
        logger.error("DB_DOWN payment retry failed card=" + cardNo + ", password=" + password + ", amount=" + amount);
    }
}
