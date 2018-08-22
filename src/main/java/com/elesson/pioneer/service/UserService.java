package com.elesson.pioneer.service;


import com.elesson.pioneer.model.User;

import java.util.List;

public interface UserService {

    boolean create(User user);

    void delete(int id);

    User get(int id);

    User getByEmail(String email);

    boolean update(User user);

    List<User> getAll();
}
