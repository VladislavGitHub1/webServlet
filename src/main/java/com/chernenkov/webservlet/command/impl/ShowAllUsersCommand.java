package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
import com.chernenkov.webservlet.entity.Medicine;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.exception.CommandException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.impl.ItemServiceImpl;
import com.chernenkov.webservlet.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.chernenkov.webservlet.command.PageName.ADMIN_PAGE;
import static com.chernenkov.webservlet.command.PageName.DB_CONTROL_PAGE;
import static com.chernenkov.webservlet.command.RequestAttribute.USER_LIST;

public class ShowAllUsersCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        List<User> userList;
        UserServiceImpl userService = UserServiceImpl.getInstance();
        try {
            userList = userService.findAll();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute(USER_LIST, userList);
        return DB_CONTROL_PAGE;
    }
}
