package com.elesson.pioneer.dao;


/**
 * The type Dao factory.
 */
public class DaoFactory {

    /**
     * Gets dao.
     *
     * @param type the type
     * @return the dao
     */
    public static BaseDao getDao(DaoType type) {
        switch (type) {
            case USER:
                return UserDaoImpl.getUserDao();
            case MOVIE:
                return MovieDaoImpl.getMovieDao();
            case EVENT:
                return EventDaoImpl.getEventDao();
            default:
                return null;
        }
    }

    public enum DaoType {
        EVENT,
        MOVIE,
        USER
    }
}
