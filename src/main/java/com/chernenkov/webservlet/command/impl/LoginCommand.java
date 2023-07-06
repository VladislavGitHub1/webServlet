package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
import com.chernenkov.webservlet.service.UserService;
import com.chernenkov.webservlet.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import static com.chernenkov.webservlet.util.RequestParameter.LOGIN;
import static com.chernenkov.webservlet.util.RequestParameter.PASS;
import static com.chernenkov.webservlet.util.RequestAttribute.USER;
import static com.chernenkov.webservlet.util.RequestAttribute.LOGIN_FAILED;
import static com.chernenkov.webservlet.util.Message.INCORRECT_LOGIN_OR_PASSWORD;
import static com.chernenkov.webservlet.util.PageName.MAIN_PAGE;
import static com.chernenkov.webservlet.util.PageName.INDEX_PAGE;


public class LoginCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASS);
        UserService userService = UserServiceImpl.getInstance();
        String page;
        if(userService.authenticate(login, password)){
            request.setAttribute(USER, login);
            page = MAIN_PAGE;
        } else {
            request.setAttribute(LOGIN_FAILED, INCORRECT_LOGIN_OR_PASSWORD);
            page = INDEX_PAGE;
        }
        return page;
    }
}
