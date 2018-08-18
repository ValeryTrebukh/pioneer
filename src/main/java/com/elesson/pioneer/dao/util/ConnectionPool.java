package com.elesson.pioneer.dao.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private ResourceBundle appRB, dbRB;
    private static ConnectionPool pool;
    private Queue<DBConnection> available, active;
    private int initialPoolSize, maxPoolSize;

    private ConnectionPool() {
        try {
            appRB = ResourceBundle.getBundle("application");
            dbRB = ResourceBundle.getBundle(appRB.getString("app.db"));
            initialPoolSize = Integer.parseInt(dbRB.getString("pool.initial"));
            maxPoolSize = Integer.parseInt(dbRB.getString("pool.max"));

            available = new ArrayBlockingQueue<>(maxPoolSize);
            active = new ArrayBlockingQueue<>(maxPoolSize);

            for(int i = 0; i < initialPoolSize; i++) {
                available.add(new DBConnection(dbRB.getString("database.driver"),
                        dbRB.getString("database.url"),
                        dbRB.getString("database.user"),
                        dbRB.getString("database.password")));
            }
            if(logger.isDebugEnabled()) logger.debug("Connection pool created size of {}", available.size());
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
        }
    }

    public static ConnectionPool getPool() {
        if (pool == null) {
            synchronized (ConnectionPool.class) {
                if (pool == null) {
                    pool = new ConnectionPool();
                }
            }
        }
        return pool;
    }

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

    private DBConnection createNewConnection() {
        DBConnection con = null;
        try {
            con = new DBConnection(dbRB.getString("database.driver"),
                    dbRB.getString("database.url"),
                    dbRB.getString("database.user"),
                    dbRB.getString("database.password"));
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
        }
        return con;
    }

    void close(DBConnection con) {
        if(active.remove(con)) {
            available.add(con);
            if(logger.isDebugEnabled()) logger.debug("Connection returned to pool");
        }
    }
}
