package com.etiya.service;

import com.etiya.logging.AppLogger;
import com.etiya.logging.LogManager;

public class ProductService {
    private final AppLogger logger;

    public ProductService(LogManager logManager) {
        this.logger = logManager.loggerFor("ProductService.list");
    }

    public void list() {
        logger.info("Product list requested");
        logger.debug("Filtering by category=all");
    }
}
