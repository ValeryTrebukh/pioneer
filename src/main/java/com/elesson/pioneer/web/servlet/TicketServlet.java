package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.model.Ticket;
import com.elesson.pioneer.model.User;
import com.elesson.pioneer.service.TicketService;
import com.elesson.pioneer.service.TicketServiceImpl;
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

public class TicketServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(TicketServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String tid = req.getParameter("tid");
        int row = Integer.parseInt(tid.split("-")[0]);
        int seat = Integer.parseInt(tid.split("-")[1]);
        int eid = Integer.parseInt(req.getParameter("eid"));

        if(isValid(new Ticket(eid, row, seat))) {
            HttpSession session = req.getSession();
            List<Ticket> preOrdered = (List<Ticket>) session.getAttribute("tickets");
            if(preOrdered == null) {
                preOrdered = new ArrayList<>();
            }
            int uid = ((User)session.getAttribute("authUser")).getId();
            addOrRemoveTicket(preOrdered, new Ticket(uid, eid, row, seat));
            session.setAttribute("tickets", preOrdered);
            logger.debug(tid + " added to list");
        }
        resp.sendRedirect("event?action=view&eid=" + eid);
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
        List<Ticket> preOrdered = (List<Ticket>) session.getAttribute("tickets");
        if(preOrdered!=null && !preOrdered.isEmpty()) {
            TicketService service = TicketServiceImpl.getTicketService();
            service.saveAll(preOrdered);
            session.removeAttribute("tickets");
        }
        int eid = Integer.parseInt(req.getParameter("eid"));
        resp.sendRedirect("event?action=view&eid=" + eid);
    }
}
