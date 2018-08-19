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
    public Ticket delete(Integer id) {
        return null;
    }

    @Override
    public Ticket save(Ticket ticket) {
        return null;
    }
}
