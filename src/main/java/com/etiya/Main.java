package com.etiya;

import com.etiya.logging.AppLogger;
import com.etiya.logging.LogContext;
import com.etiya.logging.LogManager;
import com.etiya.service.PaymentService;
import com.etiya.service.ProductService;

public class Main {
    public static void main(String[] args) {
        LogManager logManager = LogManager.defaultSetup();
        ProductService productService = new ProductService(logManager);
        PaymentService paymentService = new PaymentService(logManager);

        LogContext.startRequest("REQ-1001");
        productService.list();
        paymentService.processPayment("4111-1111-1111-1111", "mypassword", 1250.50);
        LogContext.clear();

        LogContext.startRequest("REQ-1002");
        paymentService.processPaymentWithDbIssue("4000-1234-1234-9999", "secret123", 999.99);
        LogContext.clear();

        // Sprint 5 demo: one extra target receives ERROR logs.
        AppLogger orderLogger = logManager.loggerFor("OrderService.create");
        for (int i = 1; i <= 100; i++) {
            orderLogger.error("Order failed count=" + i);
        }

        logManager.shutdown();
    }
}
