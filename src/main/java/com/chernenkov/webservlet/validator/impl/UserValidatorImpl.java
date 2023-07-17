package com.chernenkov.webservlet.validator.impl;

import com.chernenkov.webservlet.validator.UserValidator;

public class UserValidatorImpl implements UserValidator {
    @Override
    public boolean loginValidate(String login) {
        boolean isValid = false;
        if (login.matches(VALID_LOGIN)){
            isValid = true;
        }
        return isValid;
    }

    @Override
    public boolean passwordValidate(String password) {
        boolean isValid = false;
        if (password.matches(VALID_PASSWORD)){
            isValid = true;
        }
        return isValid;
    }

    @Override
    public boolean nameValidate(String name) {
        boolean isValid = false;
        if (name.matches(VALID_NAME)){
            isValid = true;
        }
        return isValid;
    }

    @Override
    public boolean lastnameValidate(String lastname) {
        boolean isValid = false;
        if (lastname.matches(VALID_LASTNAME)){
            isValid = true;
        }
        return isValid;
    }
}
