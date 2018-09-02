package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.jdbc.JDBCDao;

public class DaoStrategyFactory {

    public static Dao getStrategy(Strategy strategy) {
        switch (strategy) {
            case JDBC:
                return new JDBCDao();
            default:
                throw new EnumConstantNotPresentException(Strategy.class, strategy.name());
        }

    }

    public enum Strategy {
        JDBC
    }
}
