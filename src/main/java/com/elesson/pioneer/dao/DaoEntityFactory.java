package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.impl.*;

public class DaoEntityFactory {

    public static UserDao getUserDao() {
        return UserDaoImpl.getDao();
    }

    public static MovieDao getMovieDao() {
        return MovieDaoImpl.getDao();
    }

    public static EventDao getEventDao() {
        return EventDaoImpl.getDao();
    }

    public static TicketDao getTicketDao() {
        return TicketDaoImpl.getDao();
    }
}
