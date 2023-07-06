package com.chernenkov.webservlet.dao;

import com.chernenkov.webservlet.entity.User;

import java.util.List;

public interface UserDao {
    boolean authenticate(String login, String password);
    List<User> insertUser(User user);
}
