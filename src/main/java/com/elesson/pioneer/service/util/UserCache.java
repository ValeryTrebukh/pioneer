package com.elesson.pioneer.service.util;

import com.elesson.pioneer.dao.UserDaoImpl;
import com.elesson.pioneer.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides local storage of list of users.
 * Every modification operation (create, update, delete) clears the stored list.
 *
 */
public class UserCache {

    private static volatile List<User> users = new ArrayList<>();

    public static List<User> getUsers() {
        if(users.isEmpty()) {
            synchronized (UserCache.class) {
                if(users.isEmpty()) {
                    users = UserDaoImpl.getUserDao().getAll();
                }
            }
        }
        return users;
    }

    public static synchronized void invalidate() {
        users.clear();
    }
}
