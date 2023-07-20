package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.exception.CommandException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.UserService;
import com.chernenkov.webservlet.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Logger;

import static com.chernenkov.webservlet.command.RequestAttribute.*;
import static com.chernenkov.webservlet.command.RequestParameter.LOGIN;
import static com.chernenkov.webservlet.command.RequestParameter.PASS;
import static com.chernenkov.webservlet.command.Message.INCORRECT_LOGIN_OR_PASSWORD;
import static com.chernenkov.webservlet.command.PageName.MAIN_PAGE;
import static com.chernenkov.webservlet.command.PageName.INDEX_PAGE;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;


public class LoginCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASS);
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String page;
        try {
            Optional<User> opt = userService.authenticate(login, password);
            if (opt.isPresent()) {
                User temp = opt.get();
                logger.info (temp);
                request.setAttribute(USER, login);
                session.setAttribute(USER_NAME, login);
                session.setAttribute(USER_TYPE, temp.getUserType());
                page = MAIN_PAGE;
            } else {
                request.setAttribute(LOGIN_FAILED, INCORRECT_LOGIN_OR_PASSWORD);
                page = INDEX_PAGE;
            }
            session.setAttribute("previous_page", page);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
