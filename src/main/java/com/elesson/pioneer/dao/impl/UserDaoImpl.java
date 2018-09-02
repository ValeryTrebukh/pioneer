package com.elesson.pioneer.dao.impl;

import com.elesson.pioneer.dao.Dao;
import com.elesson.pioneer.dao.DaoStrategyFactory;
import com.elesson.pioneer.dao.UserDao;
import com.elesson.pioneer.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.elesson.pioneer.dao.DaoStrategyFactory.getStrategy;

/**
 * This class provides implementation of all {@code UserDao} interface methods.
 */
public class UserDaoImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private Dao simpleDao = getStrategy(DaoStrategyFactory.Strategy.JDBC);

    private static volatile UserDaoImpl userDao;

    private UserDaoImpl() {}

    public static UserDaoImpl getUserDao() {
        if(userDao==null) {
            synchronized (UserDaoImpl.class) {
                if(userDao==null) {
                    userDao = new UserDaoImpl();
                }
            }
        }
        if(logger.isDebugEnabled()) logger.debug("UserDao received");
        return userDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users";
        return simpleDao.getAll(User.class, query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User save(User user) {
        if (user.isNew()) {
            String query = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
            return simpleDao.save(user, query, user.getName(), user.getEmail(), user.getPassword(), User.Role.CLIENT.toString());
        } else {
            if(user.getPassword().isEmpty()) {
                String query = "UPDATE users SET name=?, email=?, role=? WHERE uid=?";
                return simpleDao.update(user, query, user.getName(), user.getEmail(), user.getRole().toString(), user.getId());
            } else {
                String query = "UPDATE users SET name=?, email=?, password=?, role=? WHERE uid=?";
                return simpleDao.update(user, query, user.getName(), user.getEmail(), user.getPassword(), user.getRole().toString(), user.getId());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM users WHERE uid=?;";
        return simpleDao.delete(query, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getByEmail(String email) {
        String query = "SELECT * FROM users WHERE email=?";
        return getUser(query, email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getById(int id) {
        String query = "SELECT * FROM users WHERE uid=?";
        return getUser(query, id);
    }

    /**
     * Retrieves the database for single object of {@code Entity} class.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @param query the SQL query string
     * @param values the parameters for SQL query
     * @return the instance of {@code User} class
     */
    private User getUser(String query, Object... values) {
        return simpleDao.get(User.class, query, values);
    }

}
