package com.chernenkov.webservlet.command;

import com.chernenkov.webservlet.command.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum CommandType {
    ADD_USER(new AddUserCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    DEFAULT(new DefaultCommand()),
    GO_TO_LOGIN_PAGE(new GoToLoginPageCommand()),
    GO_TO_REGISTRATION_PAGE(new GoToRegistrationPageCommand()),
    CREATE_ORDER(new CreateOrderCommand()),
    ADD_TO_ORDER(new AddToOrderCommand()),
    SHOW_ALL_MEDICINES(new ShowAllMedicinesCommand()),
    GO_TO_DB_PAGE(new GoToDBPageCommand()),
    GO_TO_ADMIN_PAGE(new GoToAdminPageCommand()),
    GO_TO_ORDER_LIST_PAGE(new GoToOrderListPage()),
    GO_TO_ORDER_PAGE(new GoToOrderPageCommand()),
    SHOW_ALL_USERS(new ShowAllUsersCommand()),
    ADD_PRODUCT(new AddProductCommand()),
    REMOVE_USER(new RemoveUserCommand());

    static Logger logger = LogManager.getLogger();
    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String commandStr) {
        Command command = DEFAULT.command;
        try {
            if (commandStr != null) {
                CommandType current = CommandType.valueOf(commandStr.toUpperCase());
                command = current.command;
            }
        } catch (IllegalArgumentException e) {
            logger.info(e.getMessage());
        }
        return command;
    }
}
