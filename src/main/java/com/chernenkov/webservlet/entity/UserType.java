package com.chernenkov.webservlet.entity;

public enum UserType {
    USER(0), ADMIN(1), GUEST(2);
    private int userRoleId;

    UserType(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public int getUserRoleId() {
        return userRoleId;
    }
}
