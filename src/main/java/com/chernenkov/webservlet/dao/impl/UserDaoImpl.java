package com.chernenkov.webservlet.dao.impl;

import com.chernenkov.webservlet.dao.BaseDao;
import com.chernenkov.webservlet.dao.UserDao;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.exception.DaoException;
import com.chernenkov.webservlet.pool.ConnectionPool;
import com.chernenkov.webservlet.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    static Logger logger = LogManager.getLogger();
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
    public List<User> insertUser(User user) throws DaoException {
        List<User> userList = new ArrayList<>();
        try (ProxyConnection proxyConnection = ConnectionPool.getInstance().getProxyConnection();
             Statement statement = proxyConnection.createStatement();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(INSERT_USER)) {
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
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
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
    public boolean authenticate(String login, String password) throws DaoException {
        boolean match = false;
        try (ProxyConnection proxyConnection = ConnectionPool.getInstance().getProxyConnection();
             PreparedStatement statement = proxyConnection.prepareStatement(GET_PASSWORD_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            String passFromDb;
            if (resultSet.next()) {
                passFromDb = resultSet.getString("password");
                match = password.equals(passFromDb);
            }
            resultSet.close();
        } catch (SQLException e) {
           throw new DaoException(e);
        }

        return match;
    }
}
