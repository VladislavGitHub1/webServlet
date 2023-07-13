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

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    static Logger logger = LogManager.getLogger();
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    private static final String INSERT_USER = "INSERT INTO users (login, password, name, lastname) VALUES (?,?,?,?)";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String GET_PASSWORD_BY_LOGIN = "SELECT password FROM users WHERE login= ?";

    private UserDaoImpl() {
    }

    private static UserDaoImpl instance = new UserDaoImpl();

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insertUser(User user) throws DaoException {
        boolean wasAdded = false;
        try (ProxyConnection proxyConnection = ConnectionPool.getInstance().getConnection();
             Statement statement = proxyConnection.createStatement();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(INSERT_USER);
        ) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getLastname());
            preparedStatement.execute();
            wasAdded = true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return wasAdded;
    }

    @Override
    public List<User> selectAllUsers() throws DaoException {
        List<User> userList = new ArrayList<>();
        try (ProxyConnection proxyConnection = ConnectionPool.getInstance().getConnection();
             Statement statement = proxyConnection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS)) {
            while (resultSet.next()) {
                User temp = new User();
                String tempLogin = resultSet.getString(LOGIN);
                String tempPassword = resultSet.getString(PASSWORD);
                temp.setLogin(tempLogin);
                temp.setPassword(tempPassword);
                userList.add(temp);
            }
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
        try (ProxyConnection proxyConnection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = proxyConnection.prepareStatement(GET_PASSWORD_BY_LOGIN);
             ResultSet resultSet = statement.executeQuery()) {
            statement.setString(1, login);
            String passFromDb;
            if (resultSet.next()) {
                passFromDb = resultSet.getString(PASSWORD);
                match = password.equals(passFromDb);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return match;
    }
}
