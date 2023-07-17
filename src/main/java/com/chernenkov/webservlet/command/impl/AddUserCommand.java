package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.exception.CommandException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.UserService;
import com.chernenkov.webservlet.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import static com.chernenkov.webservlet.command.constants.PageName.INDEX_PAGE;
import static com.chernenkov.webservlet.command.constants.RequestAttribute.*;
import static com.chernenkov.webservlet.command.constants.RequestParameter.*;

public class AddUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASS);
        String name = request.getParameter(NAME);
        String lastname = request.getParameter(LASTNAME);
        UserService userService = UserServiceImpl.getInstance();
        String page = INDEX_PAGE;
        User user = new User.Builder()
                .setLogin(login)
                .setPassword(password)
                .setName(name)
                .setLastname(lastname)
                .build();
        try {
            if (userService.register(user)) {
                request.setAttribute(AUTHENTICATE_STATUS, "You was registered");
            } else {
                request.setAttribute(AUTHENTICATE_STATUS, "Incorrect fields");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
