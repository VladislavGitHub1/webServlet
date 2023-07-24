package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
import com.chernenkov.webservlet.entity.Medicine;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.exception.CommandException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.UserService;
import com.chernenkov.webservlet.service.impl.ItemServiceImpl;
import com.chernenkov.webservlet.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import static com.chernenkov.webservlet.command.PageName.DB_CONTROL_PAGE;
import static com.chernenkov.webservlet.command.PageName.REGISTRATION_PAGE;
import static com.chernenkov.webservlet.command.RequestAttribute.ADD_PRODUCT_STATUS;
import static com.chernenkov.webservlet.command.RequestParameter.*;
import static com.chernenkov.webservlet.command.RequestParameter.LASTNAME;

public class AddProductCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String name = request.getParameter(NAME);
        String description = request.getParameter(DESCRIPTION);
        String withoutRecipe = request.getParameter(WITHOUT_RECIPE);
        String price = request.getParameter(PRICE);
        String page = DB_CONTROL_PAGE;
        ItemServiceImpl itemService = ItemServiceImpl.getInstance();
        request.setAttribute(ADD_PRODUCT_STATUS, "invalid_fields");
        try {
            if(itemService.addMedicine(name, description, price, withoutRecipe)){
                request.setAttribute(ADD_PRODUCT_STATUS, "successfully");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
