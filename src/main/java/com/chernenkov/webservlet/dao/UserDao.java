package com.chernenkov.webservlet.dao;

public interface UserDao {
    boolean authenticate(String login, String password);
}
