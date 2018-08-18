package com.elesson.pioneer.service;


import com.elesson.pioneer.dao.UserDao;
import com.elesson.pioneer.dao.UserDaoImpl;
import com.elesson.pioneer.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private static UserService service = null;

    private UserServiceImpl() {
        userDao = UserDaoImpl.getUserDao();
    }

    public static UserService getUserService() {
        if(service ==null) {
            synchronized (UserServiceImpl.class) {
                if(service ==null) {
                    service = new UserServiceImpl();
                }
            }
        }
        return service;
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public User create(User user) {
        return userDao.save(user);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public User get(int id) {
        return userDao.getById(id);
    }

    @Override
    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public void update(User user) {
        userDao.save(user);
    }

    @Override
    public List<User> getAll() {
        return userDao.getUsers();
    }

}
