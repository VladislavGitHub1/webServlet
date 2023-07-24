package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
import com.chernenkov.webservlet.entity.Medicine;
import com.chernenkov.webservlet.exception.CommandException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.impl.CommandServiceImpl;
import com.chernenkov.webservlet.service.impl.ItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.chernenkov.webservlet.command.PageName.PRODUCTS_PAGE;
import static com.chernenkov.webservlet.command.PageName.USER_PAGE;
import static com.chernenkov.webservlet.command.RequestAttribute.*;

public class AddToOrderCommand implements Command {
    //TODO
    static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page = PRODUCTS_PAGE;
        String[] selected = request.getParameterValues("products");
        List<String> selectedIdStringList = Arrays.asList(selected);
        List<Medicine> medicineList;
        for (String product : selected) {
            logger.debug(product.toString());
        }
        HttpSession session = request.getSession();
        int idUser = (int) session.getAttribute(USER_ID);
        CommandServiceImpl commandService = CommandServiceImpl.getInstance();
        ItemServiceImpl itemService = ItemServiceImpl.getInstance();
        try {
            commandService.addToOrder(selectedIdStringList, idUser);
            medicineList = itemService.takeAll();
            request.setAttribute(MEDICINES, medicineList);
            List<String> productList = new ArrayList<>();
            for (Medicine med : medicineList) {
                productList.add(med.getName());
            }
            request.setAttribute(PRODUCT_LIST_FOR_ORDER, productList);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
