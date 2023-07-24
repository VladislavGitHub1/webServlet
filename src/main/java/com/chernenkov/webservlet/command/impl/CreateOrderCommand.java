package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
import com.chernenkov.webservlet.entity.Medicine;
import com.chernenkov.webservlet.entity.Order;
import com.chernenkov.webservlet.exception.CommandException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.impl.CommandServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.chernenkov.webservlet.command.PageName.PRODUCTS_PAGE;
import static com.chernenkov.webservlet.command.PageName.USER_PAGE;
import static com.chernenkov.webservlet.command.RequestAttribute.*;
import static com.chernenkov.webservlet.command.RequestParameter.ORDER_CREATION_STATUS;

public class CreateOrderCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        CommandServiceImpl commandService = CommandServiceImpl.getInstance();
        HttpSession session = request.getSession();
        if (session.getAttribute(USER_ID) != null) {
            int userId = (int) session.getAttribute(USER_ID);
            try {
                Optional<Order> orderOptional = commandService.selectOrderById(userId);
                if (orderOptional.isEmpty()) {
                    commandService.createOrder(userId);
                    request.setAttribute(ORDER_CREATION_STATUS, "order is created, choose items");
                } else {
                    request.setAttribute(ORDER_CREATION_STATUS, "order is exists");
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
        return PRODUCTS_PAGE;
    }
}
