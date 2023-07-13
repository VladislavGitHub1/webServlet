package com.chernenkov.webservlet.controller.listener;

import com.chernenkov.webservlet.pool.ConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class ServletContextListener implements jakarta.servlet.ServletContextListener {
    static Logger logger = LogManager.getLogger();
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Context was initialized");
        ConnectionPool.getInstance();
        logger.info("Connection pool was created");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyPool();
        logger.info("Connection pool was destroyed");
    }
}
