package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
import com.chernenkov.webservlet.entity.Medicine;
import com.chernenkov.webservlet.exception.CommandException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.impl.ItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static com.chernenkov.webservlet.command.PageName.PRODUCTS_PAGE;
import static com.chernenkov.webservlet.command.PageName.USER_PAGE;
import static com.chernenkov.webservlet.command.RequestAttribute.MEDICINES;
import static com.chernenkov.webservlet.command.RequestAttribute.PRODUCT_LIST_FOR_ORDER;

public class ShowAllMedicinesCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        List<Medicine> medicineList;
        ItemServiceImpl itemService = ItemServiceImpl.getInstance();
        HttpSession session = request.getSession();
        try {
            medicineList = itemService.takeAll();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute(MEDICINES, medicineList);
        List<String> productList = new ArrayList<>();
        for(Medicine med : medicineList){
            productList.add(med.getName());
        }
        request.setAttribute(PRODUCT_LIST_FOR_ORDER, productList);
        return PRODUCTS_PAGE;
    }
}
