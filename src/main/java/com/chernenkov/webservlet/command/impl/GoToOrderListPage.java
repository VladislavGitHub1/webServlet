package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
import com.chernenkov.webservlet.entity.Order;
import com.chernenkov.webservlet.exception.CommandException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.impl.CommandServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.chernenkov.webservlet.command.PageName.ORDERS_LIST_PAGE;
import static com.chernenkov.webservlet.command.RequestAttribute.ORDERS;

public class GoToOrderListPage implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        CommandServiceImpl commandService = CommandServiceImpl.getInstance();
        List<Order> orderList;
        try {
            orderList = commandService.takeAllOrders();
            request.setAttribute(ORDERS, orderList);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return ORDERS_LIST_PAGE;
    }
}
