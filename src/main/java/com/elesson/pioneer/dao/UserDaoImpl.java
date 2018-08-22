package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.util.ConnectionPool;
import com.elesson.pioneer.dao.util.DBConnection;
import com.elesson.pioneer.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private SimpleCrudDao simpleDao = new SimpleCrudDaoImpl();

    private static UserDao userDao = null;

    private UserDaoImpl() {}

    public static UserDao getUserDao() {
        if(userDao==null) {
            synchronized (UserDaoImpl.class) {
                if(userDao==null) {
                    userDao = new UserDaoImpl();
                }
            }
        }
        if(logger.isDebugEnabled()) logger.debug("Admin userDao received");
        return userDao;
    }

    @Override
    public List<User> getUsers() {
        String query = "SELECT * FROM users u";
        return simpleDao.getAllById(User.class, query);
    }

    @Override
    public User save(User user) {
        try(DBConnection con = ConnectionPool.getPool().getConnection()) {
            PreparedStatement pst;
            if(user.isNew()) {
                String query = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
                pst = con.prepareInsertStatement(query, user.getName(), user.getEmail(), user.getPassword(), User.Role.CLIENT.toString());
                if(pst.executeUpdate()==1) {
                    ResultSet rs = pst.getGeneratedKeys();
                    rs.next();
                    user.setId(rs.getInt(1));
                    rs.close();
                }
                logger.info("New user created with id={}", user.getId());
            } else {
                if(user.getPassword().isEmpty()) {
                    String query = "UPDATE users SET name=?, email=?, role=? WHERE uid=?";
                    pst = con.prepareStatement(query, user.getName(), user.getEmail(), user.getRole().toString(), user.getId());
                } else {
                    String query = "UPDATE users SET name=?, email=?, password=? WHERE uid=?";
                    pst = con.prepareStatement(query, user.getName(), user.getEmail(), user.getPassword(), user.getRole().toString(), user.getId());
                }

                if(pst.executeUpdate()!=1) {
                    logger.error("User was not not created");
                    // TODO: throw new exception
                }
                logger.info("User data successfully updated for userId=" + user.getId());
            }
        } catch (SQLException e) {
            if(e.getMessage().contains("Duplicate")) {
                logger.error(e);
//                throw new DuplicateEntityException();
            }
            logger.error(e);
//            throw new DBException("Unable to save new record");
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM users WHERE uid=?;";
        return simpleDao.delete(query, id);
    }

    @Override
    public User getByEmail(String email) {
        String query = "SELECT * FROM users u WHERE email=?";
        return getUser(query, email);
    }

    @Override
    public User getById(int id) {
        String query = "SELECT * FROM users u WHERE uid=?";
        return getUser(query, id);
    }

    private User getUser(String query, Object... values) {
        return simpleDao.getById(User.class, query, values);
    }
}
