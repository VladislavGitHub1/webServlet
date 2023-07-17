package com.chernenkov.webservlet.pool;


import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.util.PropertiesReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ConnectionPool {
    static Logger logger = LogManager.getLogger();
    private static final int CONNECTION_CAPACITY = 8;
    private static ConnectionPool instance;
    private static PropertiesReader propertiesReader = new PropertiesReader();
    private static final String PROPERTIES_FILE_NAME = "db.properties";
    private BlockingQueue<ProxyConnection> free = new LinkedBlockingQueue<>(CONNECTION_CAPACITY);
    private BlockingQueue<ProxyConnection> used = new LinkedBlockingQueue<>(CONNECTION_CAPACITY);
    private static Lock lock = new ReentrantLock(true);
    private static AtomicBoolean isCreated = new AtomicBoolean(false);

    static {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

        } catch (SQLException e) {
            logger.fatal(e);
            throw new ExceptionInInitializerError(e.getMessage());
        }
    }

    private ConnectionPool() {
        String url = "jdbc:mysql://localhost:3306/db_online_parmacy";
        Properties prop = new Properties();
        try {
            prop.load(new FileReader(propertiesReader.getFileFromResource(PROPERTIES_FILE_NAME).toFile()));
        } catch (IOException | ServiceException e) {
            logger.fatal(e);
            throw new ExceptionInInitializerError(e.getMessage());
        }
        for (int i = 0; i < CONNECTION_CAPACITY; i++) {
            ProxyConnection proxyConnection = null;
            Connection connection = null;
            try {
                proxyConnection = new ProxyConnection(connection = DriverManager.getConnection(url, prop));
            } catch (SQLException e) {
                logger.fatal(e);
                throw new ExceptionInInitializerError(e.getMessage());
            }
            free.add(proxyConnection);
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            try {
                lock.lock();
                if (!isCreated.get()) {
                    instance = new ConnectionPool();
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }

        }
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = free.take();
            used.put(connection);
        } catch (InterruptedException e) {
            logger.error("Get connection failed");
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection proxy) {
            try {
                used.remove(proxy);
                free.put(proxy);
            } catch (InterruptedException e) {
                logger.error("Release connection failed" + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    public void destroyPool() {
        for (int i = 0; i < CONNECTION_CAPACITY; i++) {
            try {
                free.take().closeConnection();
            } catch (SQLException | InterruptedException e) {
                logger.info("Connection pool was destroyed" + e.getMessage());
            }
            try {
                DriverManager.deregisterDriver(new com.mysql.cj.jdbc.Driver());
            } catch (SQLException e) {
                logger.error("Error deregistering driver: " + e.getMessage());
                throw new ExceptionInInitializerError(e);
            }
        }
    }
}
