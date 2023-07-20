package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
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

import static com.chernenkov.webservlet.command.PageName.INDEX_PAGE;
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
        String page = INDEX_PAGE;
        User user = new User.Builder()
                .setLogin(login)
                .setPassword(password)
                .setName(name)
                .setLastname(lastname)
                .build();
        try {
            Optional<User> temp = userService.register(user);
            if (temp.get() instanceof UserDto) {
                UserDto userDto = (UserDto) temp.get();
                request.setAttribute(AUTHENTICATE_STATUS, "Incorrect fields");
                request.setAttribute("dto_login", userDto.getLogin());
                request.setAttribute("dto_password", userDto.getPassword());
                request.setAttribute("dto_name", userDto.getName());
                request.setAttribute("dto_lastname", userDto.getLastname());
            } else {
                request.setAttribute(AUTHENTICATE_STATUS, "You was registered");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
