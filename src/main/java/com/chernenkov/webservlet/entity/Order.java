package com.chernenkov.webservlet.entity;

import java.math.BigDecimal;

public class Order extends  AbstractEntity{
    private int idUser;
    private int idOrder;
    private BigDecimal totalPrice;

    public Order(int idUser, int idOrder, BigDecimal totalPrice) {
        this.idUser = idUser;
        this.idOrder = idOrder;
        this.totalPrice = totalPrice;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
