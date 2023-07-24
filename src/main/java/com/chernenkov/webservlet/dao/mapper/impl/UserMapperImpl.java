package com.chernenkov.webservlet.dao.mapper.impl;

import com.chernenkov.webservlet.dao.mapper.Mapper;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.exception.DaoException;
import com.chernenkov.webservlet.pool.ConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserMapperImpl implements Mapper<User> {
    private static final String ID_USER = "id_user";
    private static final String NAME = "name";
    private static final String LASTNAME = "lastname";
    private static final String LOGIN = "login";
//    private static final String PASSWORD = "password";
    private static final String ROLE_ID = "role_id";


    @Override
    public User map(ResultSet resultSet) throws SQLException {

        int userId = resultSet.getInt(ID_USER);
        String userLogin = resultSet.getString(LOGIN);
        String userName = resultSet.getString(NAME);
        String userLastname = resultSet.getString(LASTNAME);
        int userRoleId = resultSet.getInt(ROLE_ID);

        return new User.Builder()
                .setId(userId)
                .setLogin(userLogin)
                .setName(userName)
                .setLastname(userLastname)
                .setUserType(userRoleId)
                .build();
    }
}
