package com.chernenkov.webservlet.dao.impl;

import com.chernenkov.webservlet.dao.BaseDao;
import com.chernenkov.webservlet.dao.UserDao;
import com.chernenkov.webservlet.dao.mapper.impl.UserMapperImpl;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.exception.DaoException;
import com.chernenkov.webservlet.pool.ConnectionPool;
import com.chernenkov.webservlet.util.PasswordCoding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.chernenkov.webservlet.dao.RequestConstants.*;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    static Logger logger = LogManager.getLogger();

    private UserDaoImpl() {
    }

    private static UserDaoImpl instance = new UserDaoImpl();

    public static UserDaoImpl getInstance() {
        return instance;
    }


    @Override
    public List<User> selectAllUsers() throws DaoException {
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS)) {
            while (resultSet.next()) {
                UserMapperImpl userMapper = new UserMapperImpl();
                User temp = userMapper.map(resultSet);
                userList.add(temp);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return userList;
    }

    public Optional<User> selectUserByLogin(String login) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN)
        ) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    UserMapperImpl userMapper = new UserMapperImpl();
                    return Optional.of(userMapper.map(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public boolean insert(User user) throws DaoException {
        boolean wasAdded = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER);
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
    public boolean delete(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID);
        ) {
            preparedStatement.setInt(1, user.getId());
            int wasDeleted = preparedStatement.executeUpdate();
            return (wasDeleted > 0);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
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
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PASSWORD_BY_LOGIN)) {
            statement.setString(1, login);
            String passFromDb;
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    passFromDb = resultSet.getString(PASSWORD);
                    match = password.equals(passFromDb);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return match;
    }
}
