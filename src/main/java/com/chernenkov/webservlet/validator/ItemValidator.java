package com.chernenkov.webservlet.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public interface ItemValidator {
    String VALID_NAME = "^[A-Za-z]{1,30}$";
    String VALID_DESCRIPTION = "^[A-Za-z]{1,30}$";
    String VALID_PRICE = "\\d{1,4}(.\\d{2})";
    String VALID_WITHOUT_RECIPE = "^(true|false)$";
    boolean validateItem(Map<String, String> map);
}
