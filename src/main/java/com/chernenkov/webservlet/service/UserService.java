package com.chernenkov.webservlet.service;

import com.chernenkov.webservlet.entity.AbstractEntity;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.entity.UserDto;
import com.chernenkov.webservlet.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> authenticate(String login, String password) throws ServiceException;
    Optional<AbstractEntity> register(UserDto userDto) throws ServiceException;
    Boolean removeUser(String login, String id) throws ServiceException;
    List<User> findAll() throws ServiceException;
}
