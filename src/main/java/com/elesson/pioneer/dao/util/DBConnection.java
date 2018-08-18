package com.elesson.pioneer.dao.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DBConnection implements AutoCloseable {
    private static final Logger logger = LogManager.getLogger(DBConnection.class);
    private Connection connection;
    private Statement st;
    private PreparedStatement pst;

    DBConnection(String driver, String url, String username, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        connection = DriverManager.getConnection(url, username, password);
    }

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
            logger.error(e);
        }
    }

    public Statement createStatement() throws SQLException {
        st = connection.createStatement();
        return st;
    }

    public PreparedStatement prepareStatement(String query, Object... values) throws SQLException {
        pst = connection.prepareStatement(query);
        setValues(pst, values);
        return pst;
    }

    public PreparedStatement prepareInsertStatement(String query, Object... values) throws SQLException {
        pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        setValues(pst, values);
        return pst;
    }

    public PreparedStatement prepareDeleteStatement(String query, Object... values) throws SQLException {
        pst = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        setValues(pst, values);
        return pst;
    }

    private void setValues(PreparedStatement pst, Object[] values) throws SQLException {
        if(values == null) {
            return;
        }

        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof Date) {
                pst.setDate(i+1, (Date) values[i]);
            }
            else if (values[i] instanceof Integer) {
                pst.setInt(i+1, (Integer) values[i]);
            }
            else if (values[i] instanceof String) {
                pst.setString(i+1, (String) values[i]);
            } else {
                logger.warn("unexpected object type");
                // TODO: throw new exception
            }
        }
    }
}
