package com.chernenkov.webservlet.service;

import com.chernenkov.webservlet.entity.Medicine;
import com.chernenkov.webservlet.exception.ServiceException;

import java.util.List;

public interface ItemService {
    boolean makeOrder(String[] productsId);
    List<Medicine> takeAll() throws ServiceException;

    boolean addMedicine(String name, String description, String price, String withoutRecipe) throws ServiceException;
}
