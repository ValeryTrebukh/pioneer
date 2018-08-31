package com.elesson.pioneer.dao;

import com.elesson.pioneer.model.Entity;
import com.elesson.pioneer.model.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * This class provides implementation of all {@code BaseDao} interface methods.
 */
public class EventDaoImpl implements BaseDao {

    private static final Logger logger = LogManager.getLogger(EventDaoImpl.class);

    private JDBCDao simpleDao = new JDBCDaoImpl();

    private static volatile EventDaoImpl eventDao;

    private EventDaoImpl() {}

    public static EventDaoImpl getEventDao() {
        if(eventDao ==null) {
            synchronized (EventDaoImpl.class) {
                if(eventDao ==null) {
                    eventDao = new EventDaoImpl();
                }
            }
        }
        if(logger.isDebugEnabled()) logger.debug("EventDaoImpl received");
        return eventDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Event getById(int id) {
        String query = "SELECT e.*, s.*, m.* FROM events e INNER JOIN seances s on e.seance_id = s.sid " +
                "INNER JOIN movies m on e.movie_id = m.mid WHERE e.eid=?";
        return simpleDao.get(Event.class, query, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Event> getAllByDate(LocalDate date) {
        String query = "SELECT e.*, s.*, m.* FROM events e RIGHT JOIN seances s on e.seance_id = s.sid " +
                "INNER JOIN movies m on e.movie_id = m.mid WHERE e.date=? ORDER BY s.sid";
        return simpleDao.getAll(Event.class, query, Date.valueOf(date));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Event save(Entity entity) {
        Event event = (Event)entity;
        String query = "INSERT INTO events (movie_id, date, seance_id) VALUES (?, ?, ?)";
        return simpleDao.save(event, query, event.getMovie().getId(), Date.valueOf(event.getDate()), event.getSeance().getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM events WHERE eid=?";
        return simpleDao.delete(query, id);
    }

    //not implemented
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Event> getAll() {
        return null;
    }

    //not implemented
    /**
     * {@inheritDoc}
     */
    @Override
    public Event getByEmail(String value) {
        return null;
    }
}
