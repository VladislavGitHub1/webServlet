package com.chernenkov.webservlet.dao;

public class RequestConstants {
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";

    public static final String INSERT_USER = "INSERT INTO users (login, password, name, lastname) VALUES (?,?,?,?)";
    public static final String CREATE_ORDER = "INSERT INTO orders (id_user) VALUES (?)";
    public static final String INSERT_MEDICINE = "INSERT INTO products (name, description, without_recipe, price) VALUES (?,?,?,?)";
    public static final String SELECT_ALL_USERS = "SELECT * FROM users";
    public static final String SELECT_ORDER_ITEMS = "SELECT item_id FROM order_items WHERE order_id= ?";
    public static final String GET_PASSWORD_BY_LOGIN = "SELECT password FROM users WHERE login= ?";
    public static final String SELECT_USER_BY_LOGIN = "SELECT login, name, lastname, id_user, role_id FROM users WHERE login = ?";
    public static final String SELECT_USER_BY_ID = "SELECT login, name, lastname, id_user role_id FROM users WHERE id_user = ?";
    public static final String SELECT_ORDER_BY_ID = "SELECT id_order, id_user, total_price FROM orders WHERE id_user = ?";
    public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id_user = ?";
    public static final String DELETE_USER_BY_LOGIN = "DELETE FROM users WHERE login = ?";
    public static final String SELECT_ALL_MEDICINES = "SELECT * FROM products";
    public static final String SELECT_ALL_ORDERS = "SELECT * FROM orders";
    public static final String SELECT_MEDICINE_BY_ID = "SELECT id_product, name, description, without_recipe, price FROM products WHERE id_product = ?";
    public static final String ADD_TO_ORDER = "INSERT INTO order_items (order_id, item_id) VALUES (?,?)";

}
