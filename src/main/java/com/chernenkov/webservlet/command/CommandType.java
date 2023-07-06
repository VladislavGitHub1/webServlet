package com.chernenkov.webservlet.command;

import com.chernenkov.webservlet.command.impl.AddUserCommand;
import com.chernenkov.webservlet.command.impl.DefaultCommand;
import com.chernenkov.webservlet.command.impl.LoginCommand;
import com.chernenkov.webservlet.command.impl.LogoutCommand;

public enum CommandType {
    ADD_USER(new AddUserCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    DEFAULT(new DefaultCommand());
    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String commandStr) {
        Command command;
        if (commandStr != null) {
            CommandType current = CommandType.valueOf(commandStr.toUpperCase());
            command = current.command;
        } else {
            command = DEFAULT.command;
        }
        return command;
    }
}
