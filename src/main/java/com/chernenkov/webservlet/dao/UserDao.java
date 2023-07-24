package com.chernenkov.webservlet.dao;

import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> selectUserById(int id) throws DaoException;

    boolean authenticate(String login, String password) throws DaoException;
    Optional<User> selectUserByLogin(String login) throws DaoException;
}
