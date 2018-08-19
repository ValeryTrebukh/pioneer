package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.util.ConnectionPool;
import com.elesson.pioneer.dao.util.DBConnection;
import com.elesson.pioneer.model.Event;
import com.elesson.pioneer.model.Movie;
import com.elesson.pioneer.model.Seance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventDaoImpl implements EventDao {

    private static final Logger logger = LogManager.getLogger(EventDaoImpl.class);

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
        Event event = null;

        try(DBConnection con = ConnectionPool.getPool().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query, id);
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                event = new Event(id, rs.getDate("e.date").toLocalDate(),
                new Seance(rs.getInt("s.sid"), rs.getTime("s.time").toLocalTime()),
                        new Movie(rs.getInt("m.mid"),
                                rs.getString("m.name"),
                                rs.getString("m.genre"),
                                rs.getInt("m.duration"),
                                rs.getInt("m.year"),
                                rs.getBoolean("active")));
            }
            rs.close();
        } catch (SQLException e) {
            logger.error(e);
//            throw new DBException("Unable to obtain record");
        }
        return event;
    }

    @Override
    public List<Event> getEvents(LocalDate date) {
        //TODO cache
        List<Event> events = new ArrayList<>();
        String query = "SELECT e.*, s.*, m.* FROM events e RIGHT JOIN seances s on e.seance_id = s.sid " +
                "INNER JOIN movies m on e.movie_id = m.mid WHERE e.date=?";
        try(DBConnection con = ConnectionPool.getPool().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query, Date.valueOf(date));
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                Event event = new Event(rs.getInt("e.eid"),
                        rs.getDate("e.date").toLocalDate(),
                        new Seance(rs.getInt("s.sid"), rs.getTime("s.time").toLocalTime()),
                        new Movie(rs.getInt("m.mid"),
                                rs.getString("m.name"),
                                rs.getString("m.genre"),
                                rs.getInt("m.duration"),
                                rs.getInt("m.year"),
                                rs.getBoolean("active")));
                events.add(event);
                logger.info("Event obtained: " + event.toString());
            }
            rs.close();
        } catch (SQLException e) {
            logger.error(e);
        }
        return events;
    }

    @Override
    public Event save(Event event, Integer mid, Integer sid) {
        try(DBConnection con = ConnectionPool.getPool().getConnection()) {
            PreparedStatement pst;

            String query = "INSERT INTO events (movie_id, date, seance_id) VALUES (?, ?, ?)";
            pst = con.prepareInsertStatement(query, mid, Date.valueOf(event.getDate()), sid);
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
    public Event delete(int id) {
        String query = "SELECT * FROM events WHERE eid=?";
        Event event = null;

        try(DBConnection con = ConnectionPool.getPool().getConnection()) {
            PreparedStatement pst = con.prepareDeleteStatement(query, id);
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                event = new Event(rs.getDate("date").toLocalDate());
                rs.deleteRow();
                logger.debug("deleted event id = " + id);
            }
            rs.close();
        } catch (SQLException e) {
            logger.error(e);
        }
        return event;
    }
}
