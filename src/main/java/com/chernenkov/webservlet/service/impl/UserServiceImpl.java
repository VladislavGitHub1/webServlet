package com.chernenkov.webservlet.service.impl;

import com.chernenkov.webservlet.dao.impl.UserDaoImpl;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.exception.DaoException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.UserService;
import com.chernenkov.webservlet.validator.impl.UserValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {
    static Logger logger = LogManager.getLogger();
    private static UserServiceImpl instance = new UserServiceImpl();
    UserValidatorImpl userValidator = new UserValidatorImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String login, String password) throws ServiceException {
        boolean match = false;
        if (userValidator.loginValidate(login) && userValidator.passwordValidate(password)) {
            UserDaoImpl userDao = UserDaoImpl.getInstance();
            try {
                match = userDao.authenticate(login, password);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return match;
    }

    @Override
    public boolean register(User user) throws ServiceException {
        boolean wasRegistered = false;
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        if (userValidator.loginValidate(user.getLogin()) &&
                userValidator.passwordValidate(user.getPassword()) &&
                userValidator.nameValidate(user.getName()) && userValidator.lastnameValidate(user.getLastname())) {
            try {
                wasRegistered = userDao.insert(user);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return wasRegistered;
    }
}
