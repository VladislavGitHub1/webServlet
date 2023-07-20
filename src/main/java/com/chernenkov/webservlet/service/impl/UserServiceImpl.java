package com.chernenkov.webservlet.service.impl;

import com.chernenkov.webservlet.dao.impl.UserDaoImpl;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.entity.UserDto;
import com.chernenkov.webservlet.exception.DaoException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.UserService;
import com.chernenkov.webservlet.util.PasswordCoding;
import com.chernenkov.webservlet.validator.impl.UserValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    static Logger logger = LogManager.getLogger();
    private static UserServiceImpl instance = new UserServiceImpl();
    private static UserValidatorImpl userValidator = new UserValidatorImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<User> authenticate(String login, String password) throws ServiceException {
        if (userValidator.loginValidate(login) && userValidator.passwordValidate(password)) {
            UserDaoImpl userDao = UserDaoImpl.getInstance();
            try {
                String encryptedPassword = PasswordCoding.encryptPassword(password);
                Boolean match = userDao.authenticate(login, encryptedPassword);
                if (match) {
                    return userDao.selectUserByLogin(login);
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> register(User user) throws ServiceException {
        boolean wasRegistered = false;
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        if (userValidator.userValidate(user)) {
            String encryptedPassword = PasswordCoding.encryptPassword(user.getPassword());
            user.setPassword(encryptedPassword);
            try {
                userDao.insert(user);
                return Optional.of(user);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            logger.debug("//////////////////invalide fields");
            UserDto userDto = new UserDto();
            if (userValidator.loginValidate(user.getLogin())) {
                userDto.setLogin(user.getLogin());
            }
            if (userValidator.passwordValidate(user.getPassword())) {
                userDto.setPassword(user.getPassword());
            }
            if (userValidator.nameValidate(user.getName())) {
                userDto.setName(user.getName());
            }
            if (userValidator.lastnameValidate(user.getLastname())) {
                userDto.setLastname(user.getLastname());
            }
            return Optional.of(userDto);
        }
    }
}
