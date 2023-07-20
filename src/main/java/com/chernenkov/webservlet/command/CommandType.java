package com.chernenkov.webservlet.command;

import com.chernenkov.webservlet.command.impl.AddUserCommand;
import com.chernenkov.webservlet.command.impl.DefaultCommand;
import com.chernenkov.webservlet.command.impl.LoginCommand;
import com.chernenkov.webservlet.command.impl.LogoutCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum CommandType {
    ADD_USER(new AddUserCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    DEFAULT(new DefaultCommand());
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
