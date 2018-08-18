package com.elesson.pioneer.dao;

import com.elesson.pioneer.model.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User save(User user);

    boolean delete(int id);

    User getByEmail(String email);

    User getById(int id);

}
