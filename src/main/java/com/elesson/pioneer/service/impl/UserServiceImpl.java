package com.elesson.pioneer.service.impl;


import com.elesson.pioneer.dao.DaoEntityFactory;
import com.elesson.pioneer.dao.UserDao;
import com.elesson.pioneer.model.User;
import com.elesson.pioneer.service.UserService;
import com.elesson.pioneer.service.util.UserCache;

import java.util.List;

import static com.elesson.pioneer.service.util.Security.encrypt;
import static com.elesson.pioneer.service.util.ServiceValidation.checkNotFound;
import static com.elesson.pioneer.service.util.ServiceValidation.checkNotFoundWithId;

/**
 * Provides implementation of all {@code UserService} interface methods.
 * All modification operations invalidate cached collection of users.
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private static volatile UserService service;

    private UserServiceImpl() {
        userDao = DaoEntityFactory.getUserDao();
    }

    public static UserService getService() {
        if(service ==null) {
            synchronized (UserServiceImpl.class) {
                if(service ==null) {
                    service = new UserServiceImpl();
                }
            }
        }
        return service;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(User user) {
        checkNotFound(user, "user must not be null");
        user.setPassword(encrypt(user.getPassword()));
        checkNotFound(userDao.save(user), "user must not be null");
        UserCache.invalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(int id) {
        checkNotFoundWithId(userDao.delete(id), id);
        UserCache.invalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getById(int id) {
        return checkNotFoundWithId(userDao.getById(id), id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getByEmail(String email) {
        checkNotFound(email, "email must not be null");
        return checkNotFound(userDao.getByEmail(email), "email=" + email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(User user) {
        checkNotFound(user, "user must not be null");
        if(!user.getPassword().isEmpty()) {
            user.setPassword(encrypt(user.getPassword()));
        }
        checkNotFoundWithId(userDao.save(user), user.getId());
        UserCache.invalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAll() {

        if(UserCache.getUsers().isEmpty()) {
            UserCache.setUsers(userDao.getAll());
        }
        return UserCache.getUsers();
    }
}
