package com.elesson.pioneer.dao.impl;

import com.elesson.pioneer.dao.Dao;
import com.elesson.pioneer.dao.DaoStrategyFactory;
import com.elesson.pioneer.dao.TicketDao;
import com.elesson.pioneer.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.elesson.pioneer.dao.DaoStrategyFactory.getStrategy;

/**
 * This class provides implementation of all {@code TicketDao} interface methods.
 */
public class TicketDaoImpl implements TicketDao {

    private static final Logger logger = LogManager.getLogger(TicketDaoImpl.class);
    private Dao simpleDao = getStrategy(DaoStrategyFactory.Strategy.JDBC);

    private static volatile TicketDao dao;

    private TicketDaoImpl() {}

    public static TicketDao getDao() {
        if(dao ==null) {
            synchronized (TicketDaoImpl.class) {
                if(dao ==null) {
                    dao = new TicketDaoImpl();
                }
            }
        }
        if(logger.isDebugEnabled()) logger.debug("TicketDao received");
        return dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getAllByEventId(Integer id) {
        String query = "SELECT * FROM tickets " +
                "INNER JOIN events ON tickets.event_id = events.eid " +
                "INNER JOIN users ON tickets.user_id = users.uid " +
                "INNER JOIN movies on events.movie_id = movies.mid " +
                "INNER JOIN seances on events.seance_id = seances.sid " +
                "WHERE event_id=?";
        return getAllByForeignId(query, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getAllByUserId(Integer id) {
        String query = "SELECT * FROM tickets " +
                "INNER JOIN events ON tickets.event_id = events.eid " +
                "INNER JOIN users ON tickets.user_id = users.uid " +
                "INNER JOIN movies on events.movie_id = movies.mid " +
                "INNER JOIN seances on events.seance_id = seances.sid " +
                "WHERE user_id=?";

        return getAllByForeignId(query, id);
    }

    private List<Ticket> getAllByForeignId(String query, Integer id) {
        return simpleDao.getAll(Ticket.class, query, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int save(List<Ticket> tickets) {
        String query = "INSERT INTO tickets (event_id, user_id, row, seat) VALUES (?, ?, ?, ?)";
        int paramsLen = 4;

        int c = 0;
        Object[][] params2 = new Integer[tickets.size()][paramsLen];
        for(Ticket t : tickets) {
            params2[c][0] = t.getEventId();
            params2[c][1] = t.getUserId();
            params2[c][2] = t.getRow();
            params2[c][3] = t.getSeat();
            c++;
        }

        return simpleDao.save(query, params2);
    }
}
