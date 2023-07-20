package com.chernenkov.webservlet.entity;

import java.util.Objects;

public class User extends AbstractEntity {
    private int id;
    private String login;
    private String password;
    private String name;
    private String lastname;
    private UserType userType;
    User(){}

    private User(Builder builder) {
        this.id = builder.id;
        this.login = builder.login;
        this.password = builder.password;
        this.name = builder.name;
        this.lastname = builder.lastname;
        this.userType = builder.userType;
    }

    public String getLogin() {
        return login;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }


    public String getLastname() {
        return lastname;
    }

    public int getId() {
        return id;
    }

    public UserType getUserType() {
        return userType;
    }

    public static class Builder {
        private int id;
        private String login;
        private String password;
        private String name;
        private String lastname;
        private UserType userType;

        public Builder() {

        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder setUserType(int userTypeId) {
            UserType[] userTypes = UserType.values();
            this.userType = userTypes[userTypeId];
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(name, user.name) && Objects.equals(lastname, user.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, name, lastname);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", lastname='").append(lastname).append('\'');
        sb.append(", userType=").append(userType);
        sb.append('}');
        return sb.toString();
    }
}
