package com.chernenkov.webservlet.dao.impl.constants;

public class RequestConstants {
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";

    public static final String INSERT_USER = "INSERT INTO users (login, password, name, lastname) VALUES (?,?,?,?)";
    public static final String SELECT_ALL_USERS = "SELECT * FROM users";
    public static final String GET_PASSWORD_BY_LOGIN = "SELECT password FROM users WHERE login= ?";
    public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id.user = ?";
}
