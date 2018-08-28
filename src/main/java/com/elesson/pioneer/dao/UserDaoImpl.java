package com.elesson.pioneer.dao;

import com.elesson.pioneer.model.Entity;
import com.elesson.pioneer.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

/**
 * This class provides implementation of all {@code BaseDao} interface methods.
 */
public class UserDaoImpl implements BaseDao {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private JDBCDao simpleDao = new JDBCDaoImpl();

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
        String query = "SELECT * FROM users u";
        return simpleDao.getAllById(User.class, query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User save(Entity entity) {
        User user = (User)entity;
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
    public User getByValue(String email) {
        String query = "SELECT * FROM users u WHERE email=?";
        return getUser(query, email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getById(int id) {
        String query = "SELECT * FROM users u WHERE uid=?";
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
        return simpleDao.getById(User.class, query, values);
    }


    //not implemented
    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Entity> List<T> getAllByDate(LocalDate date) {
        return null;
    }

}
