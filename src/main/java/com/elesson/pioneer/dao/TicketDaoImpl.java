package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.util.ConnectionPool;
import com.elesson.pioneer.dao.util.DBConnection;
import com.elesson.pioneer.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoImpl implements TicketDao {

    private static final Logger logger = LogManager.getLogger(TicketDaoImpl.class);

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
    public List<Ticket> getAllTicketsByEventId(Integer id) {
        String query = "SELECT * FROM tickets WHERE event_id=?";
        return getAllById(query, id);
    }

    @Override
    public List<Ticket> getAllTicketsByUserId(Integer id) {
        String query = "SELECT * FROM tickets WHERE user_id=?";
        //TODO date after today
        return getAllById(query, id);
    }

    private List<Ticket> getAllById(String query, Integer id) {
        List<Ticket> tickets = new ArrayList<>();
        try(DBConnection con = ConnectionPool.getPool().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                Ticket ticket = new Ticket(rs.getInt("tid"),
                        rs.getInt("user_id"), rs.getInt("event_id"),
                        rs.getInt("row"), rs.getInt("seat"));
                tickets.add(ticket);
                logger.info("Ticket obtained: " + ticket.toString());
            }
            rs.close();
        } catch (SQLException e) {
            logger.error(e);
        }

        return tickets;
    }

    @Override
    public int save(List<Ticket> tickets) {
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
