package com.chernenkov.webservlet.service.impl;

import com.chernenkov.webservlet.dao.impl.ItemDaoImpl;
import com.chernenkov.webservlet.dao.impl.UserDaoImpl;
import com.chernenkov.webservlet.entity.AbstractEntity;
import com.chernenkov.webservlet.entity.Medicine;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.entity.UserDto;
import com.chernenkov.webservlet.exception.DaoException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.UserService;
import com.chernenkov.webservlet.util.PasswordCoding;
import com.chernenkov.webservlet.validator.impl.UserValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static com.chernenkov.webservlet.command.RequestAttribute.REMOVE_STATUS;

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
    public Optional<AbstractEntity> register(UserDto userDto) throws ServiceException {
        boolean wasRegistered = false;
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        if (userValidator.userValidate(userDto)) {
            String encryptedPassword = PasswordCoding.encryptPassword(userDto.getPassword());
            User user = new User.Builder()
                    .setLogin(userDto.getLogin())
                    .setPassword(encryptedPassword)
                    .setName(userDto.getName())
                    .setLastname(userDto.getLastname())
                    .build();
            try {
                userDao.insert(user);
                return Optional.of(user);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
//            logger.debug("//////////////////invalide fields");
//            UserDto userDto = new UserDto();
//            if (userValidator.loginValidate(user.getLogin())) {
//                userDto.setLogin(user.getLogin());
//            }
//            if (userValidator.passwordValidate(user.getPassword())) {
//                userDto.setPassword(user.getPassword());
//            }
//            if (userValidator.nameValidate(user.getName())) {
//                userDto.setName(user.getName());
//            }
//            if (userValidator.lastnameValidate(user.getLastname())) {
//                userDto.setLastname(user.getLastname());
//            }
            return Optional.of(userDto);
        }
    }

    @Override
    public Boolean removeUser(String login, String id) throws ServiceException {
        boolean was_deleted = false;
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        User user;
        if (userValidator.loginValidate(login)) {
            user = new User.Builder()
                    .setLogin(login)
                    .build();
            try {
                was_deleted = userDao.delete(user);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
            return was_deleted;
        } else if (userValidator.idValidate(id)) {
            logger.debug("ID is valid");
            int idInt = Integer.parseInt(id);
            user = new User.Builder()
                    .setId(idInt)
                    .build();
            try {
                was_deleted = userDao.delete(user);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
            return was_deleted;
        }
        return was_deleted;
    }

    @Override
    public List<User> findAll() throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        List<User> users;
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return users;
    }
}
