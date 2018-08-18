package com.elesson.pioneer.service;


import com.elesson.pioneer.model.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User create(User user);

    void delete(int id);

    User get(int id);

    User getByEmail(String email);

    void update(User user);

    List<User> getAll();
}
