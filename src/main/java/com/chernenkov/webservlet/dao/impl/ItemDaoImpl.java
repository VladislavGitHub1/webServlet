package com.chernenkov.webservlet.dao.impl;

import com.chernenkov.webservlet.dao.BaseDao;
import com.chernenkov.webservlet.dao.ItemDao;
import com.chernenkov.webservlet.dao.mapper.impl.ItemMapperImpl;
import com.chernenkov.webservlet.dao.mapper.impl.UserMapperImpl;
import com.chernenkov.webservlet.entity.Medicine;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.exception.DaoException;
import com.chernenkov.webservlet.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.chernenkov.webservlet.dao.RequestConstants.*;

public class ItemDaoImpl extends BaseDao<Medicine> implements ItemDao {
    private ItemDaoImpl() {
    }

    private static ItemDaoImpl instance = new ItemDaoImpl();

    public static ItemDaoImpl getInstance() {
        return instance;
    }
    private static ItemMapperImpl itemMapper = new ItemMapperImpl();

    @Override
    public boolean insert(Medicine medicine) throws DaoException {
        boolean wasAdded = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MEDICINE);
        ) {
            int withoutRecipeDB;
            if (medicine.isWithoutRecipe()){
                withoutRecipeDB = 1;
            } else {
                withoutRecipeDB = 0;
            }
            preparedStatement.setString(1, medicine.getName());
            preparedStatement.setString(2, medicine.getDescription());
            preparedStatement.setInt(3, withoutRecipeDB);
            preparedStatement.setBigDecimal(4, medicine.getPrice());
            preparedStatement.execute();
            wasAdded = true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return wasAdded;
    }

    @Override
    public boolean delete(Medicine medicine) throws DaoException {
        return false;
    }

    @Override
    public List<Medicine> findAll() throws DaoException {
        List<Medicine> medicineList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_MEDICINES)) {
            while (resultSet.next()) {
                Medicine temp = itemMapper.map(resultSet);
                medicineList.add(temp);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return medicineList;
    }

    @Override
    public Medicine update(Medicine medicine) throws DaoException {
        return null;
    }

    @Override
    public Optional<Medicine> selectMedicineById(int id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MEDICINE_BY_ID)
        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(itemMapper.map(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return Optional.empty();
    }
}
