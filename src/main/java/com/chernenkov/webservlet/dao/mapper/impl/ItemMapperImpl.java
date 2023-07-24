package com.chernenkov.webservlet.dao.mapper.impl;

import com.chernenkov.webservlet.dao.mapper.Mapper;
import com.chernenkov.webservlet.entity.Medicine;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemMapperImpl implements Mapper<Medicine> {
    private static final String ID_PRODUCT = "id_product";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String WITHOUT_RECIPE = "without_recipe";
    private static final String PRICE = "price";
    @Override
    public Medicine map(ResultSet resultSet) throws SQLException {
        int itemId = resultSet.getInt(ID_PRODUCT);
        String name = resultSet.getString(NAME);
        String description = resultSet.getString(DESCRIPTION);
        int withoutRecipeInt = resultSet.getInt(WITHOUT_RECIPE);
        BigDecimal price = resultSet.getBigDecimal(PRICE);
        return new Medicine.Builder()
                .setId(itemId)
                .setName(name)
                .setWithoutRecipe(withoutRecipeInt)
                .setDescription(description)
                .setPrice(price)
                .build();
    }
}
