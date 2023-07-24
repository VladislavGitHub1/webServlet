package com.chernenkov.webservlet.service.impl;

import com.chernenkov.webservlet.dao.impl.ItemDaoImpl;
import com.chernenkov.webservlet.entity.Medicine;
import com.chernenkov.webservlet.exception.DaoException;
import com.chernenkov.webservlet.exception.ServiceException;
import com.chernenkov.webservlet.service.ItemService;
import com.chernenkov.webservlet.validator.impl.ItemValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemServiceImpl implements ItemService {
    static Logger logger = LogManager.getLogger();
    private static ItemServiceImpl instance = new ItemServiceImpl();
    private static ItemValidatorImpl itemValidator = new ItemValidatorImpl();

    private ItemServiceImpl() {
    }

    public static ItemServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean makeOrder(String[] productsId) {
        return false;
    }

    @Override
    public List<Medicine> takeAll() throws ServiceException {
        ItemDaoImpl itemDao = ItemDaoImpl.getInstance();
        List<Medicine> medicines;
        try {
            medicines = itemDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return medicines;
    }

    @Override
    public boolean addMedicine(String name, String description, String price, String withoutRecipe) throws ServiceException {
        ItemDaoImpl itemDao = ItemDaoImpl.getInstance();
        Map<String, String> map = new HashMap<>();
        logger.debug(price);
        map.put("name", name);
        map.put("description", description);
        map.put("price", price);
        map.put("withoutRecipe", withoutRecipe);
        Medicine medicine = null;
        if (itemValidator.validateItem(map)){
            Boolean withoutRecipeBoolean = Boolean.parseBoolean(withoutRecipe);
            BigDecimal priceDecimal = new BigDecimal(price);
            medicine = new Medicine.Builder()
                    .setName(name)
                    .setDescription(description)
                    .setWithoutRecipe(withoutRecipeBoolean)
                    .setPrice(priceDecimal)
                    .build();
        }
        if (medicine !=null) {
            try {
               return itemDao.insert(medicine);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return false;
    }
}
