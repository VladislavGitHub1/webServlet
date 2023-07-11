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

        } catch (
                SQLException e) {
            logger.fatal(e);
            throw new ExceptionInInitializerError();
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
                throw new ExceptionInInitializerError();
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

    public ProxyConnection getProxyConnection() {
        ProxyConnection proxyConnection = null;
        try {
            proxyConnection = free.take();
            used.put(proxyConnection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return proxyConnection;
    }

    public void releaseProxyConnection(ProxyConnection proxyConnection) {
        try {
            if (proxyConnection.getConnection() instanceof Connection) {
                used.remove(proxyConnection);
                free.put(proxyConnection);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void totallyCloseConnection(ProxyConnection proxyConnection) {
        try {
            proxyConnection.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
