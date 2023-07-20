package com.chernenkov.webservlet.service;

import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    Optional<User> authenticate(String login, String password) throws ServiceException;
    Optional<User> register(User user) throws ServiceException;
}
