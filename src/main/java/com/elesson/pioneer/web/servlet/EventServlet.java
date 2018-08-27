package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.dao.exception.DuplicateEntityException;
import com.elesson.pioneer.model.*;
import com.elesson.pioneer.service.*;
import com.elesson.pioneer.service.exception.NotFoundEntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Handles requests on event entity.
 * Behavior depends on request attribute 'action'.
 * Possible values:
 *      view - redirects to page describing event details and shows a map of cinema hall,
 *      where user can acquire a seat.
 *      create or delete - available only for admins.
 *  Performs some validation of request attributes and redirects to error page in case of
 *  some exception came from service/dao layer.
 */
public class EventServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(EventServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        EventService service = EventServiceImpl.getEventService();
        MovieService mService = MovieServiceImpl.getMovieService();
        TicketService tService = TicketServiceImpl.getTicketService();

        String action = req.getParameter("action");
        String eid = req.getParameter("eid");
        String date = req.getParameter("date");

        User aUser = (User)req.getSession().getAttribute("authUser");

        try {
            switch (action == null ? "view" : action) {
                case "create":
                    if(aUser!=null && aUser.getRole()==User.Role.ADMIN) {
                        req.setAttribute("event", new Event(LocalDate.parse(date)));
                        req.setAttribute("action", action);
                        req.setAttribute("movies", mService.getActiveMovies());
                        req.getRequestDispatcher("jsp/eventForm.jsp").forward(req, resp);
                    }
                    break;
                case "delete":
                    if(aUser!=null && aUser.getRole()==User.Role.ADMIN) {
                        service.delete(Integer.parseInt(eid));
                        resp.sendRedirect("schedule" + getAdd(req));
                    }
                    break;
                case "view":
                    int eventId = Integer.parseInt(eid);
                    req.setAttribute("event", service.getEvent(eventId));

                    int rows = Integer.parseInt(req.getServletContext().getInitParameter("hallRows"));
                    int seats = Integer.parseInt(req.getServletContext().getInitParameter("hallSeats"));
                    Hall hall = new Hall(rows, seats);
                    hall.place(tService.getAllTicketsByEventId(eventId));
                    validatePreorders(req, resp);
                    hall.place((List<Ticket>) req.getSession().getAttribute("tickets"));
                    req.setAttribute("hall", hall);
                    req.getRequestDispatcher("jsp/eventView.jsp").forward(req, resp);
                    break;
                default:
                    resp.sendRedirect("schedule");
                    break;
            }
        } catch (DateTimeParseException | NumberFormatException | NotFoundEntityException e) {
            logger.warn(e.getMessage());
            resp.setStatus(404);
        } catch (DBException e) {
            logger.error(e);
            resp.setStatus(500);
        }
    }

    private String getAdd(HttpServletRequest req) {
        String referer = req.getHeader("referer");
        return referer.lastIndexOf("?") != -1 ? referer.substring(referer.lastIndexOf("?")) : "";
    }

    private void validatePreorders(HttpServletRequest req, HttpServletResponse resp) {
        List<Ticket> tickets = (List<Ticket>) req.getSession().getAttribute("tickets");
        if(tickets!=null && !tickets.isEmpty() && tickets.get(0).getEventId()!=Integer.parseInt(req.getParameter("eid"))) {
            logger.info("Invalidation pre-orders: {} removed", tickets.size());
            tickets.clear();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String mid = req.getParameter("mid");
        String sid = req.getParameter("sid");
        String date = req.getParameter("date");

        EventService service = EventServiceImpl.getEventService();
        Event event = null;
        try{
            event = new Event(null, LocalDate.parse(date),
                    new Seance(Integer.parseInt(sid)), new Movie(Integer.parseInt(mid)));
            service.save(event);
            resp.sendRedirect("schedule?date=" + date);
        } catch (DateTimeParseException | NumberFormatException e) {
            logger.warn("Parsing error", e.getMessage());
            resp.setStatus(404);
        } catch (DuplicateEntityException de) {
            logger.warn(de);
            req.setAttribute("duplicate", true);
            req.setAttribute("movies", MovieServiceImpl.getMovieService().getActiveMovies());
            req.setAttribute("event", event);
            req.getRequestDispatcher("jsp/eventForm.jsp").forward(req, resp);
        } catch (DBException e) {
            logger.error(e);
            resp.setStatus(500);
        }
    }
}
