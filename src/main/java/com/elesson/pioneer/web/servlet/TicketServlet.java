package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.model.Ticket;
import com.elesson.pioneer.model.User;
import com.elesson.pioneer.service.TicketService;
import com.elesson.pioneer.service.impl.TicketServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.elesson.pioneer.web.util.Constants.*;
import static com.elesson.pioneer.web.util.Helper.getBackReference;

/**
 * Handles operations on tickets for some event in the cinema.
 * Perform some validation of data from request params.
 * A User may pre-order the ticket (it will be saved into session) but it does not
 * prevent the fact that ticket will be acquired by another User.
 */
public class TicketServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(TicketServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String tid = req.getParameter(A_TID);

        try {
            int row = Integer.parseInt(tid.split("-")[0]);
            int seat = Integer.parseInt(tid.split("-")[1]);
            int eid = Integer.parseInt(req.getParameter(A_EID));
            if(isValid(new Ticket(eid, row, seat))) {
                HttpSession session = req.getSession();
                List<Ticket> preOrdered = (List<Ticket>) session.getAttribute(A_TICKETS);
                if(preOrdered == null) {
                    preOrdered = new ArrayList<>();
                }
                User aUser = (User)session.getAttribute(A_AUTH_USER);
                if(aUser == null) {
                    resp.setStatus(403);
                } else {
                    int uid = aUser.getId();
                    addOrRemoveTicket(preOrdered, new Ticket(uid, eid, row, seat));
                    session.setAttribute(A_TICKETS, preOrdered);
                    logger.debug(tid + " added to list");
                    resp.sendRedirect(EVENT + getBackReference(req));
                }
            }
        } catch (NumberFormatException e) {
            logger.warn(e.getMessage());
            resp.setStatus(404);
        } catch (DBException e) {
            logger.error(e);
            resp.setStatus(500);
        }
    }

    private void addOrRemoveTicket(List<Ticket> tickets, Ticket ticket) {
        for(int i = 0; i < tickets.size(); i++) {
            Ticket t = tickets.get(i);
            if(t.getRow().equals(ticket.getRow()) && t.getSeat().equals(ticket.getSeat())) {
                tickets.remove(i);
                return;
            }
        }
        tickets.add(ticket);
    }

    private boolean isValid(Ticket ticket) {
        TicketService tService = TicketServiceImpl.getTicketService();
        for(Ticket t : tService.getAllTicketsByEventId(ticket.getEventId())) {
            if(t.getRow().equals(ticket.getRow()) && t.getSeat().equals(ticket.getSeat())) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<Ticket> preOrdered = (List<Ticket>) session.getAttribute(A_TICKETS);
        if(preOrdered!=null && !preOrdered.isEmpty()) {
            TicketService service = TicketServiceImpl.getTicketService();
            service.saveAll(preOrdered);
            session.removeAttribute(A_TICKETS);
        }
        resp.sendRedirect(EVENT + getBackReference(req));
    }
}
