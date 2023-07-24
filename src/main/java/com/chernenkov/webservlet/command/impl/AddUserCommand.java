package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
import com.chernenkov.webservlet.entity.AbstractEntity;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.entity.UserDto;
import com.chernenkov.webservlet.exception.CommandException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.UserService;
import com.chernenkov.webservlet.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chernenkov.webservlet.command.PageName.*;
import static com.chernenkov.webservlet.command.RequestAttribute.*;
import static com.chernenkov.webservlet.command.RequestParameter.*;

public class AddUserCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASS);
        String name = request.getParameter(NAME);
        String lastname = request.getParameter(LASTNAME);
        UserService userService = UserServiceImpl.getInstance();
        String page = REGISTRATION_PAGE;
        UserDto userDto = new UserDto();
        userDto.setLogin(login);
        userDto.setPassword(password);
        userDto.setName(name);
        userDto.setLastname(lastname);
        try {
            Optional<AbstractEntity> temp = userService.register(userDto);
            if (temp.get() instanceof UserDto) {
                UserDto userDtoTemp = (UserDto) temp.get();
                request.setAttribute(AUTHENTICATE_STATUS, "Incorrect fields");
                request.setAttribute("dto_login", userDtoTemp.getLogin());
                request.setAttribute("dto_password", userDtoTemp.getPassword());
                request.setAttribute("dto_name", userDtoTemp.getName());
                request.setAttribute("dto_lastname", userDtoTemp.getLastname());
            } else {
                request.setAttribute(AUTHENTICATE_STATUS, "You was registered");
                page = AUTHORIZATION_PAGE;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
