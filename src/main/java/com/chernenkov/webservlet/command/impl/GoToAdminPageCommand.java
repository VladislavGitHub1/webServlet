package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
import com.chernenkov.webservlet.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static com.chernenkov.webservlet.command.PageName.ADMIN_PAGE;

public class GoToAdminPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return ADMIN_PAGE;
    }
}