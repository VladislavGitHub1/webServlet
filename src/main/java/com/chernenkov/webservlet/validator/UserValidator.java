package com.chernenkov.webservlet.validator;

public interface UserValidator {
    String VALID_LOGIN= "^[A-Za-z]([\\.A-Za-z0-9-]{1,18})([A-Za-z0-9])$";
    String VALID_PASSWORD = "^[A-Za-z0-9]{1,30}$";
    String VALID_NAME = "^[A-Za-z]{1,30}$";
    String VALID_LASTNAME = "^[A-Za-z]{1,30}$";
    boolean loginValidate(String login);
    boolean passwordValidate(String password);
    boolean nameValidate(String name);
    boolean lastnameValidate(String lastname);
}
