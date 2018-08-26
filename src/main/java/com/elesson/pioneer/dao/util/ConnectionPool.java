package com.elesson.pioneer.dao.util;

import com.elesson.pioneer.dao.exception.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * The {@code ConnectionPool} class handles the database connection pool.
 * This class is responsible for initializing of pool properties,
 * producing connections for DAO layer objects, and closing the connections.
 */
public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private ResourceBundle appRB, dbRB;
    private static volatile ConnectionPool pool;
    private Queue<DBConnection> available, active;
    private int initialPoolSize, maxPoolSize;

    /**
     * Method is responsible for obtaining connection pool.
     *
     * @return the connectionPool
     * @throws DBException the general exception to cover all SQL exceptions
     */
    public static ConnectionPool getPool() throws DBException {
        if (pool == null) {
            synchronized (ConnectionPool.class) {
                if (pool == null) {
                    pool = new ConnectionPool();
                }
            }
        }
        return pool;
    }

    /**
     * Read the application properties and initiates pool of connections with basic number
     *
     * @throws DBException if file with settings is not found
     *
     */
    private ConnectionPool() throws DBException {
        try {
            appRB = ResourceBundle.getBundle("application");
            dbRB = ResourceBundle.getBundle(appRB.getString("app.db"));
            initialPoolSize = Integer.parseInt(dbRB.getString("pool.initial"));
            maxPoolSize = Integer.parseInt(dbRB.getString("pool.max"));

            available = new ArrayBlockingQueue<>(maxPoolSize);
            active = new ArrayBlockingQueue<>(maxPoolSize);

            initConnectionPool();

            if(logger.isDebugEnabled()) logger.debug("Connection pool created size of {}", available.size());
        } catch (MissingResourceException e) {
            logger.error("DB settings file not found at {}", appRB.getString("app.db"));
            logger.error(e);
            throw new DBException("DB settings not found");
        }
    }

    /**
     * Fills pool with basic number of connections.
     *
     */
    private void initConnectionPool() {
        for(int i = 0; i < initialPoolSize; i++) {
            available.add(createNewConnection());
        }
    }

    /**
     * Obtains connections from pool.
     * If no available connections in the pool the new connection will be created
     * and added to pool up to pool's max size
     *
     * @return the connection
     */
    public synchronized DBConnection getConnection() {
        DBConnection con = null;
        while(con == null) {
            if(!available.isEmpty()) {
                con = available.poll();
                if(logger.isDebugEnabled()) logger.debug("Got connection from pool");
            }
            else if(available.isEmpty() && active.size() < maxPoolSize) {
                con = createNewConnection();
                if(logger.isDebugEnabled()) logger.debug("New extra connection created");
            }
        }
        active.add(con);
        return con;
    }


    /**
     * Creates a new DB connection
     *
     * @return the connection
     */
    private DBConnection createNewConnection() {
        DBConnection con = null;
        try {
            con = new DBConnection(dbRB.getString("database.driver"),
                    dbRB.getString("database.url"),
                    dbRB.getString("database.user"),
                    dbRB.getString("database.password"));
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Connection was not created", e);
            throw new DBException("Connection was not created");
        }
        return con;
    }

    /**
     * Returns connection to pool.
     */
    void close(DBConnection con) {
        if(active.remove(con)) {
            available.add(con);
            if(logger.isDebugEnabled()) logger.debug("Connection returned to pool");
        }
    }
}
