package com.elesson.pioneer.dao.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

public class DBHelper {
    private static final Logger dLogger = LogManager.getLogger(DBHelper.class);

    public static void initDB() {
        if(dLogger.isDebugEnabled()) dLogger.debug("Initializing database");
        try {
            Statement st = ConnectionPool.getPool().getConnection().createStatement();
            ResourceBundle appRB = ResourceBundle.getBundle("application");

            if(appRB.getString("app.db_reset").equals("false")) {
                return;
            }

            Scanner sc = new Scanner(DBHelper.class.getResourceAsStream(appRB.getString("app.initdb")));
            if(dLogger.isDebugEnabled()) dLogger.debug("Reading file");
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
                    if(dLogger.isDebugEnabled()) dLogger.debug("Execute statement: " + sb.toString());
                    sb = new StringBuilder();
                } else {
                    sb.append(line.trim()).append(" ");
                }
            }
            if(dLogger.isDebugEnabled()) dLogger.debug("Database initialized");
        } catch (SQLException e) {
            dLogger.error(e);
        }
    }

    public static void showResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for(int i = 1; i <= columnCount; i++) {
            if(i > 1) {
                System.out.print(", ");
            }
            System.out.print(metaData.getColumnLabel(i));
        }
        System.out.println();

        while(rs.next()) {
            for(int i = 1; i <= columnCount; i++) {
                if(i > 1) {
                    System.out.print(", ");
                }
                System.out.print(rs.getString(i));
            }
            System.out.println();
        }
    }

    public static String getResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        StringBuilder sb = new StringBuilder();
        int columnCount = metaData.getColumnCount();
        for(int i = 1; i <= columnCount; i++) {
            if(i > 1) {
                sb.append(", ");
            }
            sb.append(metaData.getColumnLabel(i));
        }
        sb.append("\n");

        while(rs.next()) {
            for(int i = 1; i <= columnCount; i++) {
                if(i > 1) {
                    sb.append(", ");
                }
                sb.append(rs.getString(i));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
