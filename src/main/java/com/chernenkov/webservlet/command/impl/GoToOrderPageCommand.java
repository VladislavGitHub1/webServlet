package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
import com.chernenkov.webservlet.entity.Medicine;
import com.chernenkov.webservlet.exception.CommandException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.impl.CommandServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.List;

import static com.chernenkov.webservlet.command.PageName.*;
import static com.chernenkov.webservlet.command.RequestAttribute.*;

public class GoToOrderPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        CommandServiceImpl commandService = CommandServiceImpl.getInstance();
        String page = PRODUCTS_PAGE;
        HttpSession session = request.getSession();
        List<Medicine> addedMedicines;
        if(session.getAttribute(USER_ID) !=null) {
            int userId = (int) session.getAttribute(USER_ID);
            try {
                addedMedicines = commandService.goToOrderPage(userId);
                BigDecimal totalPrice = new BigDecimal(0);
                for(Medicine med : addedMedicines){
                    totalPrice = totalPrice.add(med.getPrice());
                }
                request.setAttribute(TOTAL_PRICE, totalPrice);
                page = ORDER_PAGE;
                request.setAttribute(MEDICINES_ORDERED, addedMedicines);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
        return page;
    }
}
