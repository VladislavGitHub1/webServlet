package com.chernenkov.webservlet.dao;

import com.chernenkov.webservlet.entity.Medicine;
import com.chernenkov.webservlet.entity.Order;
import com.chernenkov.webservlet.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface CommandDao {

    boolean addToOrder(List<Medicine> medicineList, int Id) throws DaoException;
    List<Integer> selectOrderItems(int id) throws DaoException;

    Optional<Order> selectOrderById(int userId) throws DaoException;

    boolean createOrder(int userId) throws DaoException;
}
