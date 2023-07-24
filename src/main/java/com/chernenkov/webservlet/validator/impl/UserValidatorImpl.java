package com.chernenkov.webservlet.validator.impl;

import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.entity.UserDto;
import com.chernenkov.webservlet.validator.UserValidator;

public class UserValidatorImpl implements UserValidator {

    public boolean userValidate(UserDto userDto) {
        boolean isValid = true;
        if(!userDto.getLogin().matches(VALID_LOGIN)){
            userDto.setLogin("incorrect field");
            isValid = false;
        }
        if(!userDto.getPassword().matches(VALID_PASSWORD)){
            userDto.setPassword("");
            isValid = false;
        }
        if(!userDto.getName().matches(VALID_NAME)){
            userDto.setName("incorrect field");
            isValid = false;
        }
        if(!userDto.getLastname().matches(VALID_LASTNAME)){
            userDto.setLastname("incorrect field");
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean loginValidate(String login) {
        if (login != null) {
            boolean isValid = login.matches(VALID_LOGIN);
            return isValid;
        } else return false;
    }

    @Override
    public boolean passwordValidate(String password) {
        if (password != null) {
            boolean isValid = password.matches(VALID_PASSWORD);
            return isValid;
        } else return false;
    }

    @Override
    public boolean nameValidate(String name) {
        if (name != null) {
            boolean isValid = name.matches(VALID_NAME);
            return isValid;
        } else return false;
    }

    @Override
    public boolean lastnameValidate(String lastname) {
        if (lastname != null) {
            boolean isValid = lastname.matches(VALID_LASTNAME);
            return isValid;
        } else return false;
    }

    @Override
    public boolean idValidate(String id) {
        boolean isValid = false;
        if (id != null) {
            isValid = id.matches(VALID_ID);
        }
        return isValid;
    }
}
