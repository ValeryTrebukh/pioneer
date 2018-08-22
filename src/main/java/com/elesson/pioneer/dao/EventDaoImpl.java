package com.elesson.pioneer.dao;

import com.elesson.pioneer.model.Entity;
import com.elesson.pioneer.model.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class EventDaoImpl implements BaseDao {

    private static final Logger logger = LogManager.getLogger(EventDaoImpl.class);

    private SimpleCrudDao simpleDao = new SimpleCrudDaoImpl();

    private static BaseDao eventDao = null;

    private EventDaoImpl() {}

    public static BaseDao getEventDao() {
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

    @Override
    public Event getById(int id) {
        //TODO read from cache
        String query = "SELECT e.*, s.*, m.* FROM events e INNER JOIN seances s on e.seance_id = s.sid " +
                "INNER JOIN movies m on e.movie_id = m.mid WHERE e.eid=?";
        return simpleDao.getById(Event.class, query, id);
    }

    @Override
    public List<Event> getAllByDate(LocalDate date) {
        //TODO cache
        String query = "SELECT e.*, s.*, m.* FROM events e RIGHT JOIN seances s on e.seance_id = s.sid " +
                "INNER JOIN movies m on e.movie_id = m.mid WHERE e.date=? ORDER BY s.sid";
        return simpleDao.getAllById(Event.class, query, Date.valueOf(date));
    }

    @Override
    public boolean save(Entity entity) {
        Event event = (Event)entity;
        String query = "INSERT INTO events (movie_id, date, seance_id) VALUES (?, ?, ?)";
        return simpleDao.save(event, query, event.getMovie().getId(), Date.valueOf(event.getDate()), event.getSeance().getId());
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM events WHERE eid=?";
        return simpleDao.delete(query, id);
    }

    @Override
    public List<Event> getAll() {
        return null;
    }

    @Override
    public List<Event> getActive() {
        return null;
    }

    @Override
    public Event getByEmail(String email) {
        return null;
    }
}
