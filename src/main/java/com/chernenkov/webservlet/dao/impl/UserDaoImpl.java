package com.chernenkov.webservlet.dao.impl;

import com.chernenkov.webservlet.dao.BaseDao;
import com.chernenkov.webservlet.dao.UserDao;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    private static final String INSERT_USER = "INSERT INTO users (login, password) VALUES (?,?)";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String GET_PASSWORD_BY_LOGIN = "SELECT password FROM users WHERE login= ?";

    private UserDaoImpl() {
    }

    private static UserDaoImpl instance = new UserDaoImpl();

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<User> insertUser(User user) {
        List<User> userList = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/web_servlet";
        Properties prop = new Properties();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.execute();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS);
            while (resultSet.next()) {
                User temp = new User();
                String tempLogin = resultSet.getString("login");
                String tempPassword = resultSet.getString("password");
                temp.setLogin(tempLogin);
                temp.setPassword(tempPassword);
                userList.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public boolean insert(User user) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean authenticate(String login, String password) {
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PASSWORD_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            String passFromDb;
            if (resultSet.next()) {
                passFromDb = resultSet.getString("password");
                match = password.equals(passFromDb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return match;
    }
}
