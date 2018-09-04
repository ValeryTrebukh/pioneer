package com.elesson.pioneer.dao.jdbc;

import com.elesson.pioneer.dao.Dao;
import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.dao.exception.DuplicateEntityException;
import com.elesson.pioneer.dao.util.ConnectionPool;
import com.elesson.pioneer.dao.util.DBConnection;
import com.elesson.pioneer.model.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides implementation of all {@code JDBCDao} interface methods.
 * Implementation based on JDBC tools.
 */
public class JDBCDao implements Dao {

    private static final Logger logger = LogManager.getLogger(JDBCDao.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(String query, int id) {
        int resultRows = 0;
        try(DBConnection con = ConnectionPool.getPool().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query, id);
            resultRows = pst.executeUpdate();
            logger.info("Deleted entity id={}", id);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DBException("Unable to delete record");
        }
        return resultRows == 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Entity> T get(Class cl, String query, Object... values) {
        T entity = null;
        try(DBConnection con = ConnectionPool.getPool().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query, values);
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                entity = (T)JDBCEntityFactory.create(cl, rs);
                logger.info("Entity obtained: " + entity.toString());
            }
            rs.close();
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e.getMessage());
        }
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Entity> List<T> getAll(Class cl, String query, Object... values) {
        List<T> list = new ArrayList<>();
        T entity = null;
        try(DBConnection con = ConnectionPool.getPool().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query, values);
            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                entity = (T)JDBCEntityFactory.create(cl, rs);
                list.add(entity);
                logger.info("Entity obtained: " + entity.toString());
            }
            rs.close();
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException("Unable to obtain record");
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Entity> T save(T entity, String query, Object... values) {
        try(DBConnection con = ConnectionPool.getPool().getConnection()) {
            PreparedStatement pst = con.prepareInsertStatement(query, values);
            if(pst.executeUpdate()==1) {
                ResultSet rs = pst.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getInt(1));
                logger.info("New entity created with id={}", entity.getId());
                rs.close();
            }
        } catch (SQLException e) {
            if(e.getMessage().contains("Duplicate")) {
                logger.error(e);
                throw new DuplicateEntityException();
            }
            logger.error(e);
            throw new DBException("Unable to saveAll new record");
        }
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Entity> int save(String query, Object[][] values) {
        int count = 0;

        try (DBConnection con = ConnectionPool.getPool().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            for(Object[] ob : values) {
                con.setValues(pst, ob);
                pst.addBatch();
                count++;
            }
            pst.executeBatch();
        } catch (SQLException e) {
            if(e.getMessage().contains("Duplicate")) {
                logger.error(e);
                throw new DuplicateEntityException();
            }
            logger.error(e);
            throw new DBException("Unable to save new records");
        }
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Entity> T update(T entity, String query, Object... values) {
        try(DBConnection con = ConnectionPool.getPool().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query, values);
            if(pst.executeUpdate()!=1) {
                logger.error("Entity was not not updated");
                throw new DBException("Unable to update record");
            }
            logger.info("User data successfully updated for id=" + entity.getId());
        } catch (SQLException e) {
            if(e.getMessage().contains("Duplicate")) {
                logger.error(e);
                throw new DuplicateEntityException();
            }
            logger.error(e);
            throw new DBException("Unable to saveAll new record");
        }
        return entity;
    }
}
