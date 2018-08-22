package com.elesson.pioneer.dao;

import com.elesson.pioneer.model.Entity;

import java.time.LocalDate;
import java.util.List;

public interface BaseDao {

    <T extends Entity> List<T> getAll();

    <T extends Entity> T getById(int id);

    boolean save(Entity entity);

    boolean delete(int id);

    <T extends Entity> T getByEmail(String email);

    <T extends Entity> List<T> getAllByDate(LocalDate date);

    <T extends Entity> List<T> getActive();
}
