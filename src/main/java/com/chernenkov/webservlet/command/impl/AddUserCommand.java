package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.exception.CommandException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.UserService;
import com.chernenkov.webservlet.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.chernenkov.webservlet.util.Message.INCORRECT_LOGIN_OR_PASSWORD;
import static com.chernenkov.webservlet.util.PageName.INDEX_PAGE;
import static com.chernenkov.webservlet.util.PageName.MAIN_PAGE;
import static com.chernenkov.webservlet.util.RequestAttribute.LOGIN_FAILED;
import static com.chernenkov.webservlet.util.RequestAttribute.USER;
import static com.chernenkov.webservlet.util.RequestParameter.*;

public class AddUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASS);
        String name = request.getParameter(NAME);
        String lastname = request.getParameter(LASTNAME);
        UserService userService = UserServiceImpl.getInstance();
        String page;
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setLastname(lastname);
        try {
            userService.register(user);
            page = INDEX_PAGE;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
