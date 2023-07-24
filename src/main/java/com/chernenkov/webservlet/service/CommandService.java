package com.chernenkov.webservlet.service;

import com.chernenkov.webservlet.entity.Order;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.entity.Medicine;

import java.util.List;
import java.util.Optional;

public interface CommandService {

    boolean addToOrder(List<String> selectedList, int id) throws ServiceException;

    Optional<Order> selectOrderById(int userId) throws ServiceException;

    List<Medicine> goToOrderPage(int idUser) throws ServiceException;
    boolean createOrder(int idUser) throws ServiceException;

    List<Order> takeAllOrders() throws ServiceException;
}
