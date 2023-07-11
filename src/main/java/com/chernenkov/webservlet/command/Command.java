package com.chernenkov.webservlet.command;

import com.chernenkov.webservlet.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest request) throws CommandException;
    default void refresh(){}
}
