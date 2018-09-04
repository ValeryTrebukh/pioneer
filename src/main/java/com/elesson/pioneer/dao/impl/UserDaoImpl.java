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

    private static final String INSERT_USER = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_USER_WITHOUT_PASSWORD = "UPDATE users SET name=?, email=?, role=? WHERE uid=?";
    private static final String UPDATE_USER_WITH_PASSWORD = "UPDATE users SET name=?, email=?, password=?, role=? WHERE uid=?";
    private static final String SELECT_USERS = "SELECT * FROM users";
    private static final String DELETE_USER = "DELETE FROM users WHERE uid=?";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email=?";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE uid=?";

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private Dao simpleDao = getStrategy(DaoStrategyFactory.Strategy.JDBC);

    private static volatile UserDao dao;

    private UserDaoImpl() {}

    public static UserDao getDao() {
        if(dao==null) {
            synchronized (UserDaoImpl.class) {
                if(dao==null) {
                    dao = new UserDaoImpl();
                }
            }
        }
        if(logger.isDebugEnabled()) logger.debug("UserDao received");
        return dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAll() {
        return simpleDao.getAll(User.class, SELECT_USERS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User save(User user) {
        if (user.isNew()) {
            return simpleDao.save(user, INSERT_USER, user.getName(), user.getEmail(), user.getPassword(), User.Role.CLIENT.toString());
        } else {
            if(user.getPassword().isEmpty()) {
                return simpleDao.update(user, UPDATE_USER_WITHOUT_PASSWORD, user.getName(), user.getEmail(), user.getRole().toString(), user.getId());
            } else {
                return simpleDao.update(user, UPDATE_USER_WITH_PASSWORD, user.getName(), user.getEmail(), user.getPassword(), user.getRole().toString(), user.getId());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(int id) {
        return simpleDao.delete(DELETE_USER, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getByEmail(String email) {
        return getUser(SELECT_USER_BY_EMAIL, email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getById(int id) {
        return getUser(SELECT_USER_BY_ID, id);
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
