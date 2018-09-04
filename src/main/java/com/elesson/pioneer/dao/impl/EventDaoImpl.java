package com.elesson.pioneer.dao.impl;

import com.elesson.pioneer.dao.Dao;
import com.elesson.pioneer.dao.EventDao;
import com.elesson.pioneer.model.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static com.elesson.pioneer.dao.DaoStrategyFactory.*;

/**
 * This class provides implementation of all {@code EventDao} interface methods.
 */
public class EventDaoImpl implements EventDao {

    private static final String GET_EVENT_BY_ID = "SELECT * FROM events INNER JOIN seances on events.seance_id = seances.sid " +
            "INNER JOIN movies on events.movie_id = movies.mid WHERE events.eid=?";
    private static final String GET_EVENT_BY_DATE = "SELECT * FROM events RIGHT JOIN seances on events.seance_id = seances.sid " +
            "INNER JOIN movies on events.movie_id = movies.mid WHERE events.date=? ORDER BY seances.sid";
    private static final String INSERT_EVENT = "INSERT INTO events (movie_id, date, seance_id) VALUES (?, ?, ?)";
    private static final String DELETE_EVENT = "DELETE FROM events WHERE eid=?";

    private static final Logger logger = LogManager.getLogger(EventDaoImpl.class);
    private Dao simpleDao = getStrategy(Strategy.JDBC);

    private static volatile EventDao dao;

    private EventDaoImpl() {}

    public static EventDao getDao() {
        if(dao ==null) {
            synchronized (EventDaoImpl.class) {
                if(dao ==null) {
                    dao = new EventDaoImpl();
                }
            }
        }
        if(logger.isDebugEnabled()) logger.debug("EventDaoImpl received");
        return dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Event getById(int id) {
        return simpleDao.get(Event.class, GET_EVENT_BY_ID, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Event> getByDate(LocalDate date) {
        return simpleDao.getAll(Event.class, GET_EVENT_BY_DATE, Date.valueOf(date));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Event save(Event event) {
        return simpleDao.save(event, INSERT_EVENT, event.getMovie().getId(), Date.valueOf(event.getDate()), event.getSeance().getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(int id) {
        return simpleDao.delete(DELETE_EVENT, id);
    }
}
