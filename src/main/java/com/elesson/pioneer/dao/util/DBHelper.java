package com.elesson.pioneer.dao.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * The type Db helper.
 */
public class DBHelper {
    private static final Logger dLogger = LogManager.getLogger(DBHelper.class);

    /**
     * Initializes/re-initializes database with basic data based on application settings.
     */
    public static void initDB() {
        ResourceBundle appRB = ResourceBundle.getBundle("application");
        if(appRB.getString("app.db_reset").equals("true")) {
            dLogger.info("Re-initializing database");
            if(dLogger.isDebugEnabled()) dLogger.debug("Initializing database started");
            try {
                Statement st = ConnectionPool.getPool().getConnection().createStatement();
                Scanner sc = new Scanner(DBHelper.class.getResourceAsStream(appRB.getString("app.initdb")));
                StringBuilder sb = new StringBuilder();

                while(true) {
                    if(!sc.hasNextLine()) {
                        break;
                    }
                    String line = sc.nextLine();
                    if(line.trim().endsWith(";")) {
                        line = line.trim();
                        line = line.substring(0, line.length() - 1);
                        sb.append(line);
                        st.execute(sb.toString());
                        if(dLogger.isDebugEnabled()) dLogger.debug("Executing statement: " + sb.toString());
                        sb = new StringBuilder();
                    } else {
                        sb.append(line.trim()).append(" ");
                    }
                }
                if(dLogger.isDebugEnabled()) dLogger.debug("Database initialized successfully");
            } catch (SQLException e) {
                dLogger.error(e);
            }
        } else {
            dLogger.info("Database was not re-initialized. Existing database content is used");
        }
    }
}
