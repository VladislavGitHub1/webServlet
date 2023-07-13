package com.chernenkov.webservlet.dao;

import com.chernenkov.webservlet.entity.AbstractEntity;
import com.chernenkov.webservlet.exception.DaoException;

import java.util.List;

public abstract class BaseDao<T extends AbstractEntity> {
   public abstract boolean insert(T t) throws DaoException;
   public abstract boolean delete(T t) throws DaoException;
   public abstract List<T> findAll() throws DaoException;
   public abstract T update(T t) throws DaoException;

}
