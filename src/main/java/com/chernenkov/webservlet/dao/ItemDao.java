package com.chernenkov.webservlet.dao;

import com.chernenkov.webservlet.entity.Medicine;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.exception.DaoException;

import java.util.Optional;

public interface ItemDao {
    Optional<Medicine> selectMedicineById(int id) throws DaoException;
}
