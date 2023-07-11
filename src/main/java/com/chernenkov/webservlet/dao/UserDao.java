package com.chernenkov.webservlet.dao;

import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.exception.DaoException;

import java.util.List;

public interface UserDao {
    boolean authenticate(String login, String password) throws DaoException;
    List<User> insertUser(User user) throws DaoException;
}
