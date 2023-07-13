package com.chernenkov.webservlet.pool;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ConnectionPool {
    static Logger logger = LogManager.getLogger();
    private static final int CONNECTION_CAPACITY = 8;
    private static ConnectionPool instance;
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
        prop.put("user", "Starlet");
        prop.put("password", "Vlad1111");
        prop.put("autoReconnect", "true");
        prop.put("characterEncoding", "UTF-8");
        prop.put("useUnicode", "true");
        prop.put("useSSL", "true");
        prop.put("useJDBCCompliantTimezoneShift", "true");
        prop.put("useLegacyDatetimeCode", "false");
        prop.put("serverTimezone", "UTC");
        prop.put("serverSslCert", "classpath:server.crt");
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

    public ProxyConnection getConnection() {
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

    public void releaseProxyConnection(Connection connection) {
        try {
            if (connection instanceof ProxyConnection) {
                used.remove((ProxyConnection) connection);
                free.put((ProxyConnection) connection);
            }

        } catch (InterruptedException e) {
            logger.error("Release connection failed");
            Thread.currentThread().interrupt();
        }
    }

    public void destroyPool() {
        for (int i = 0; i < CONNECTION_CAPACITY; i++) {
            try {
                free.take().closeConnection();
            } catch (SQLException | InterruptedException e) {
                logger.info("Connection pool was destroyed");
            }
        }
    }
//    public void deregisterDriver(){
//        DriverManager.deregisterDriver();
//    }

}
