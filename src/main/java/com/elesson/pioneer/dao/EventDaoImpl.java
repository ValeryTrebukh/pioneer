package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.util.ConnectionPool;
import com.elesson.pioneer.dao.util.DBConnection;
import com.elesson.pioneer.model.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EventDaoImpl implements EventDao {

    private static final Logger logger = LogManager.getLogger(EventDaoImpl.class);

    private SimpleCrudDao simpleDao = new SimpleCrudDaoImpl();

    private static EventDao eventDao = null;

    private EventDaoImpl() {}

    public static EventDao getEventDao() {
        if(eventDao ==null) {
            synchronized (EventDaoImpl.class) {
                if(eventDao ==null) {
                    eventDao = new EventDaoImpl();
                }
            }
        }
        if(logger.isDebugEnabled()) logger.debug("EventDao received");
        return eventDao;
    }

    @Override
    public Event getEvent(Integer id) {
        //TODO read from cache
        String query = "SELECT e.*, s.*, m.* FROM events e INNER JOIN seances s on e.seance_id = s.sid " +
                "INNER JOIN movies m on e.movie_id = m.mid WHERE e.eid=?";
        return simpleDao.getById(Event.class, query, id);
    }

    @Override
    public List<Event> getEvents(LocalDate date) {
        //TODO cache
        String query = "SELECT e.*, s.*, m.* FROM events e RIGHT JOIN seances s on e.seance_id = s.sid " +
                "INNER JOIN movies m on e.movie_id = m.mid WHERE e.date=? ORDER BY s.sid";
        return simpleDao.getAllById(Event.class, query, Date.valueOf(date));
    }

    @Override
    public Event save(Event event) {
        try(DBConnection con = ConnectionPool.getPool().getConnection()) {
            PreparedStatement pst;

            String query = "INSERT INTO events (movie_id, date, seance_id) VALUES (?, ?, ?)";
            pst = con.prepareInsertStatement(query, event.getMovie().getId(), Date.valueOf(event.getDate()), event.getSeance().getId());
            if(pst.executeUpdate()==1) {
                ResultSet rs = pst.getGeneratedKeys();
                rs.next();
                event.setId(rs.getInt(1));
                rs.close();
            }
            logger.info("New event created with id={}", event.getId());
        } catch (SQLException e) {
            if(e.getMessage().contains("Duplicate")) {
                logger.error(e);
//                throw new DuplicateEntityException();
            }
            logger.error(e);
//            throw new DBException("Unable to save new record");
        }
        return event;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM events WHERE eid=?";
        return simpleDao.delete(query, id);
    }
}
