package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
import com.chernenkov.webservlet.entity.Medicine;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.entity.UserType;
import com.chernenkov.webservlet.exception.CommandException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.UserService;
import com.chernenkov.webservlet.service.impl.ItemServiceImpl;
import com.chernenkov.webservlet.service.impl.UserServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Logger;

import static com.chernenkov.webservlet.command.PageName.*;
import static com.chernenkov.webservlet.command.RequestAttribute.*;
import static com.chernenkov.webservlet.command.RequestParameter.LOGIN;
import static com.chernenkov.webservlet.command.RequestParameter.PASS;
import static com.chernenkov.webservlet.command.Message.INCORRECT_LOGIN_OR_PASSWORD;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class LoginCommand implements Command {
    static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASS);
        UserService userService = UserServiceImpl.getInstance();
        ItemServiceImpl itemService = ItemServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String page;
        try {
            Optional<User> opt = userService.authenticate(login, password);
            if (opt.isPresent()) {
                User temp = opt.get();
                logger.info(temp);
                request.setAttribute(USER, login);
                session.setAttribute(USER_NAME, login);
                session.setAttribute(USER_TYPE, temp.getUserType());
                session.setAttribute(USER_ID, temp.getId());
                page = USER_PAGE;
                if (temp.getUserType() == UserType.ADMIN) {
                    page = ADMIN_PAGE;
                }
                List<Medicine> medicineList = itemService.takeAll();
                request.setAttribute(MEDICINES, medicineList);
                List<String> productList = new ArrayList<>();
                for (Medicine med : medicineList) {
                    productList.add(med.getName());
                }
                request.setAttribute(PRODUCT_LIST_FOR_ORDER, productList);
                logger.debug("medicenes list: " + medicineList.toString());
            } else {
                request.setAttribute(LOGIN_FAILED, INCORRECT_LOGIN_OR_PASSWORD);
                page = AUTHORIZATION_PAGE;
            }
            session.setAttribute("previous_page", page);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
