package com.elesson.pioneer.dao;

import com.elesson.pioneer.model.Entity;

import java.util.List;

public interface SimpleCrudDao {

    <T extends Entity> T save(String query, T entity);

    boolean delete(String query, int id);

    <T extends Entity> T getById(Class cl, String query, Object... values);

    <T extends Entity> List<T> getAllById(Class cl, String query, Object... values);

    <T extends Entity> T save(T entity);
}
