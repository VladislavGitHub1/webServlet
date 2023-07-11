package com.chernenkov.webservlet.service;

import com.chernenkov.webservlet.exception.ServiceException;

public interface UserService {
    boolean authenticate(String login, String password) throws ServiceException;
}
