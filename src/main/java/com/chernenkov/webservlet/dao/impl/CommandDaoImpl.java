package com.chernenkov.webservlet.dao.impl;

import com.chernenkov.webservlet.dao.BaseDao;
import com.chernenkov.webservlet.dao.CommandDao;
import com.chernenkov.webservlet.entity.Medicine;
import com.chernenkov.webservlet.entity.Order;
import com.chernenkov.webservlet.entity.User;
import com.chernenkov.webservlet.exception.DaoException;
import com.chernenkov.webservlet.pool.ConnectionPool;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.chernenkov.webservlet.dao.RequestConstants.*;

public class CommandDaoImpl extends BaseDao<Order> implements CommandDao {
    private static final String ITEM_ID = "item_id";
    private static final String ORDER_ID = "id_order";
    private static final String USER_ID = "id_user";
    private static final String TOTAL_PRICE = "total_price";


    private CommandDaoImpl() {
    }

    private static CommandDaoImpl instance = new CommandDaoImpl();

    public static CommandDaoImpl getInstance() {
        return instance;
    }
    @Override
    public Optional<Order> selectOrderById(int userId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_ID)
        ) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int orderId = resultSet.getInt(ORDER_ID);
                    int userIdFromDB = resultSet.getInt(USER_ID);
                    BigDecimal totalPrice = resultSet.getBigDecimal(TOTAL_PRICE);
                    return Optional.of(new Order(userIdFromDB, orderId, totalPrice));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean insert(Order order) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Order order) throws DaoException {
        return false;
    }

    @Override
    public Order update(Order order) throws DaoException {
        return null;
    }

    @Override
    public boolean createOrder(int userId) throws DaoException {
        boolean wasAdded = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.execute();
            wasAdded = true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return wasAdded;
    }

    @Override
    public boolean addToOrder(List<Medicine> medicineList, int orderId) throws DaoException {
        boolean wasAdded = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_ORDER);
        ) {
            for (Medicine med : medicineList) {
                preparedStatement.setInt(1, orderId);
                preparedStatement.setInt(2, med.getId());
                preparedStatement.execute();
                wasAdded = true;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return wasAdded;
    }

    @Override
    public List<Integer> selectOrderItems(int orderId) throws DaoException {
        List<Integer> orderedMedicines = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_ITEMS)) {
            preparedStatement.setInt(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int idItem = resultSet.getInt(ITEM_ID);
                    orderedMedicines.add(idItem);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderedMedicines;
    }
    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_ORDERS)) {
            while (resultSet.next()) {
                int orderId = resultSet.getInt(ORDER_ID);
                int userIdFromDB = resultSet.getInt(USER_ID);
                BigDecimal totalPrice = resultSet.getBigDecimal(TOTAL_PRICE);
                Order temp = new Order(userIdFromDB,orderId, totalPrice);
                orderList.add(temp);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return orderList;
    }
}
