package com.elesson.pioneer.service.util;

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
        return users;
    }

    public static void setUsers(List<User> users) {
        UserCache.users = users;
    }

    public static void invalidate() {
        users.clear();
    }
}
