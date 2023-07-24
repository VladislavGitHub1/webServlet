package com.chernenkov.webservlet.service.impl;

import com.chernenkov.webservlet.service.AdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminServiceImpl implements AdminService {
    static Logger logger = LogManager.getLogger();
    private static AdminServiceImpl instance = new AdminServiceImpl();
    private AdminServiceImpl() {
    }
    public static AdminServiceImpl getInstance() {
        return instance;
    }
}

