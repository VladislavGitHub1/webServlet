package com.chernenkov.webservlet.service.impl;

import com.chernenkov.webservlet.dao.impl.CommandDaoImpl;
import com.chernenkov.webservlet.dao.impl.ItemDaoImpl;
import com.chernenkov.webservlet.entity.Medicine;
import com.chernenkov.webservlet.entity.Order;
import com.chernenkov.webservlet.exception.DaoException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.CommandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandServiceImpl implements CommandService {
    static Logger logger = LogManager.getLogger();
    private static CommandServiceImpl instance = new CommandServiceImpl();

    private CommandServiceImpl() {
    }

    public static CommandServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean addToOrder(List<String> selectedIdStringList, int idUser) throws ServiceException {
        CommandDaoImpl commandDao = CommandDaoImpl.getInstance();
        List<Medicine> medicineList = new ArrayList<>();
        int idOrder;
        for (String str : selectedIdStringList) {
            int idItem = Integer.parseInt(str);
            ItemDaoImpl itemDao = ItemDaoImpl.getInstance();
            try {
                Optional<Medicine> temp = itemDao.selectMedicineById(idItem);
                if (!temp.isEmpty()) {
                    Medicine tempMedicine = temp.get();
                    medicineList.add(tempMedicine);
                }
                Optional<Order> orderOptional = commandDao.selectOrderById(idUser);
                if (!orderOptional.isEmpty()) {
                    idOrder = orderOptional.get().getIdOrder();
                    return commandDao.addToOrder(medicineList, idOrder);
                }
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    @Override
    public boolean createOrder(int idUser) throws ServiceException {
        CommandDaoImpl commandDao = CommandDaoImpl.getInstance();
        try {
            commandDao.createOrder(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return false;
    }

    @Override
    public Optional<Order> selectOrderById(int userId) throws ServiceException {
        CommandDaoImpl commandDao = CommandDaoImpl.getInstance();
        try {
            Optional<Order> orderOptional = commandDao.selectOrderById(userId);
            return orderOptional;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Medicine> goToOrderPage(int idUser) throws ServiceException {
        CommandDaoImpl commandDao = CommandDaoImpl.getInstance();
        ItemDaoImpl itemDao = ItemDaoImpl.getInstance();
        List<Medicine> orderedMedicines = new ArrayList<>();
        try {
            Optional<Order> orderOptional = commandDao.selectOrderById(idUser);
            if (!orderOptional.isEmpty()) {
                int idOrder = orderOptional.get().getIdOrder();
                List<Integer> orderedItemsId = commandDao.selectOrderItems(idOrder);
                for (Integer itemId : orderedItemsId) {
                    Optional<Medicine> temp = itemDao.selectMedicineById(itemId);
                    if (!temp.isEmpty()) {
                        orderedMedicines.add(temp.get());
                    }
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orderedMedicines;
    }
    @Override
    public List<Order> takeAllOrders() throws ServiceException {
        CommandDaoImpl commandDao = CommandDaoImpl.getInstance();
        List<Order> orders;
        try {
            orders = commandDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orders;
    }
}
