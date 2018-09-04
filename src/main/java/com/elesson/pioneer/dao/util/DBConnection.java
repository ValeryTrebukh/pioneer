package com.elesson.pioneer.dao.util;

import com.elesson.pioneer.dao.exception.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * The {@code DBConnection} class handles the database connection.
 * This class is a wrapper of {@code java.sql.Connection} class.
 */
public class DBConnection implements AutoCloseable {
    private static final Logger logger = LogManager.getLogger(DBConnection.class);
    private Connection connection;
    private Statement st;
    private PreparedStatement pst;

    DBConnection(String driver, String url, String username, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        connection = DriverManager.getConnection(url, username, password);
    }

    /**
     * Method is responsible for closing resources.
     */
    @Override
    public void close() {
        try {
            if(st!=null) {
                st.close();
                if(logger.isDebugEnabled()) logger.debug("Statement closed");
            }
            if(pst!=null) {
                pst.close();
                if(logger.isDebugEnabled()) logger.debug("PreparedStatement closed");
            }
            ConnectionPool.getPool().close(this);
            if(logger.isDebugEnabled()) logger.debug("DBConnection closed");
        } catch (SQLException e) {
            logger.error("Unable to close connection", e);
        }
    }

    /**
     * Wraps the {@code java.sql.Statement#setAutoCommit} method
     *
     * @throws SQLException the SQL exception
     */
    public void setAutoCommit(boolean value) throws SQLException {
        connection.setAutoCommit(value);
    }

    /**
     * Wraps the {@code java.sql.Statement#commit} method
     *
     * @throws SQLException the SQL exception
     */
    public void commit() throws SQLException {
        connection.commit();
    }

    /**
     * Wraps the {@code java.sql.Statement#rollback} method
     *
     * @throws SQLException the SQL exception
     */
    public void rollback() throws SQLException {
        connection.rollback();
    }

    /**
     * Initializes the {@code java.sql.Statement} object
     *
     * @return the       {@code Statement} object
     * @throws SQLException the SQL exception
     */
    public Statement createStatement() throws SQLException {
        st = connection.createStatement();
        return st;
    }

    /**
     * Method is responsible for creating of {@code java.sql.PreparedStatement} object
     *
     * @param query      the query
     * @param values     the values to be set into statement
     * @return the       {@code PreparedStatement} object
     * @throws SQLException the SQL exception
     */
    public PreparedStatement prepareStatement(String query, Object... values) throws SQLException {
        pst = connection.prepareStatement(query);
        setValues(pst, values);
        return pst;
    }

    /**
     * Method is responsible for creating of {@code java.sql.PreparedStatement} object
     * for SQL INSERT query
     *
     * @param query      the query
     * @param values     the values to be set into statement
     * @return the       {@code PreparedStatement} object
     * @throws SQLException the SQL exception
     */
    public PreparedStatement prepareInsertStatement(String query, Object... values) throws SQLException {
        pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        setValues(pst, values);
        return pst;
    }

    /**
     * Method is responsible for creating of {@code java.sql.PreparedStatement} object
     * for SQL DELETE query
     *
     * @param query      the query
     * @param values     the values to be set into statement
     * @return the       {@code PreparedStatement} object
     * @throws SQLException the SQL exception
     */
    public PreparedStatement prepareDeleteStatement(String query, Object... values) throws SQLException {
        pst = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        setValues(pst, values);
        return pst;
    }

    public void setValues(PreparedStatement pst, Object[] values) throws SQLException {
        if(values == null) {
            return;
        }

        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof Date) {
                pst.setDate(i+1, (Date) values[i]);
            }
            else if (values[i] instanceof Boolean) {
                pst.setBoolean(i+1, (Boolean) values[i]);
            }
            else if (values[i] instanceof Integer) {
                pst.setInt(i+1, (Integer) values[i]);
            }
            else if (values[i] instanceof String) {
                pst.setString(i+1, (String) values[i]);
            } else {
                logger.warn("Unexpected object type");
                throw new DBException("unable to parse arguments");
            }
        }
    }
}
