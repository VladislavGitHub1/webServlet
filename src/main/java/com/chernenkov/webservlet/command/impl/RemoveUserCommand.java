package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.exception.CommandException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.UserService;
import com.chernenkov.webservlet.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import static com.chernenkov.webservlet.command.PageName.DB_CONTROL_PAGE;
import static com.chernenkov.webservlet.command.RequestAttribute.*;
import static com.chernenkov.webservlet.command.RequestParameter.*;

public class RemoveUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page = DB_CONTROL_PAGE;
        String login = request.getParameter(LOGIN);
        String id = request.getParameter(ID_USER);
        UserService userService = UserServiceImpl.getInstance();
        if(login.isEmpty() && id.isEmpty()){
            request.setAttribute(REMOVE_STATUS, "Empty fields");
            return page;
        }
        if(id.equals("0")){
            request.setAttribute(REMOVE_STATUS, "FAILED TO REMOVE MAIN ADMINISTRATOR");
        }
        try {
            if(userService.removeUser(login, id)){
                request.setAttribute(REMOVE_STATUS, "successfully");
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return page;
    }
}
