package com.mundial.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> {
    int save(T entity) throws SQLException;
    List<T> findAll() throws SQLException;
    T findById(int id) throws SQLException;
}
