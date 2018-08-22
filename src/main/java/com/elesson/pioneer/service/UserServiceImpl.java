package com.elesson.pioneer.service;


import com.elesson.pioneer.dao.BaseDao;
import com.elesson.pioneer.dao.DaoFactory;
import com.elesson.pioneer.model.User;

import java.util.List;

import static com.elesson.pioneer.service.util.Security.encrypt;

public class UserServiceImpl implements UserService {

    private BaseDao userDao;
    private static UserService service = null;

    private UserServiceImpl() {
        userDao = DaoFactory.getDao(DaoFactory.DaoType.USER);
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
    public boolean create(User user) {
        user.setPassword(encrypt(user.getPassword()));
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
    public boolean update(User user) {
        if(!user.getPassword().isEmpty()) {
            user.setPassword(encrypt(user.getPassword()));
        }
        return userDao.save(user);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

}
