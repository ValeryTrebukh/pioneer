package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.util.ConnectionPool;
import com.elesson.pioneer.dao.util.DBConnection;
import com.elesson.pioneer.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TicketDaoImpl implements TicketDao {

    private static final Logger logger = LogManager.getLogger(TicketDaoImpl.class);

    private SimpleCrudDao simpleDao = new SimpleCrudDaoImpl();

    private static TicketDao ticketDao = null;

    private TicketDaoImpl() {}

    public static TicketDao getTicketDao() {
        if(ticketDao ==null) {
            synchronized (TicketDaoImpl.class) {
                if(ticketDao ==null) {
                    ticketDao = new TicketDaoImpl();
                }
            }
        }
        if(logger.isDebugEnabled()) logger.debug("TicketDao received");
        return ticketDao;
    }
    @Override
    public List<Ticket> getAllByEventId(Integer id) {
        String query = "SELECT * FROM tickets t " +
                "INNER JOIN events e ON t.event_id = e.eid " +
                "INNER JOIN users u ON t.user_id = u.uid " +
                "INNER JOIN movies m on e.movie_id = m.mid " +
                "INNER JOIN seances s on e.seance_id = s.sid " +
                "WHERE event_id=?";
        return getAllById(query, id);
    }

    @Override
    public List<Ticket> getAllByUserId(Integer id) {
        String query = "SELECT * FROM tickets t " +
                "INNER JOIN events e ON t.event_id = e.eid " +
                "INNER JOIN users u ON t.user_id = u.uid " +
                "INNER JOIN movies m on e.movie_id = m.mid " +
                "INNER JOIN seances s on e.seance_id = s.sid " +
                "WHERE user_id=?";
        //TODO date after today
        return getAllById(query, id);
    }

    private List<Ticket> getAllById(String query, Integer id) {
        return simpleDao.getAllById(Ticket.class, query, id);
    }

    @Override
    public int saveAll(List<Ticket> tickets) {
        int count = 0;
        DBConnection con = ConnectionPool.getPool().getConnection();
        try {
            PreparedStatement pst;
            String query = "INSERT INTO tickets (event_id, user_id, row, seat) VALUES (?, ?, ?, ?)";
            con.setAutoCommit(false);
            for(Ticket t : tickets) {
                pst = con.prepareInsertStatement(query, t.getEventId(), t.getUserId(), t.getRow(), t.getSeat());
                if(pst.executeUpdate()==1) {
                    ResultSet rs = pst.getGeneratedKeys();
                    rs.next();
                    t.setId(rs.getInt(1));
                    rs.close();
                }
                logger.info("New ticket created with id={}", t.getId());
                count++;
            }
            con.commit();
        } catch (SQLException e) {
            if(e.getMessage().contains("Duplicate")) {
                logger.error(e);
//                throw new DuplicateEntityException();
            }
            logger.error(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                logger.error(e1);
            }
//            throw new DBException("Unable to save new records");
        }
        finally {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
        return count;
    }
}
