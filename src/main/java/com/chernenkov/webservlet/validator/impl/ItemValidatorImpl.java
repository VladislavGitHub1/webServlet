package com.chernenkov.webservlet.validator.impl;

import com.chernenkov.webservlet.validator.ItemValidator;

import java.util.Map;

public class ItemValidatorImpl implements ItemValidator {
    public boolean validateItem(Map<String, String> map) {
        boolean mapIsValid = false;
            if (!map.values().contains(null) && map.get("name").matches(VALID_NAME) && map.get("description").matches(VALID_DESCRIPTION)
                    && map.get("price").matches(VALID_PRICE) && map.get("withoutRecipe").matches(VALID_WITHOUT_RECIPE)) {
                mapIsValid = true;
            }
        return mapIsValid;
    }

}
