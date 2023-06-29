package com.chernenkov.webservlet.service;

public interface UserService {
    boolean authenticate(String login, String password);
}
