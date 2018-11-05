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

import static com.elesson.pioneer.web.util.Constants.*;
import static com.elesson.pioneer.web.util.Helper.getBackReference;

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
    private MovieService movieService = ServiceFactory.getMovieService();
    private EventService service = ServiceFactory.getEventService();
    private TicketService tService = ServiceFactory.getTicketService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter(A_ACTION);
        String eid = req.getParameter(A_EID);
        String date = req.getParameter(A_DATE);

        User aUser = (User)req.getSession().getAttribute(A_AUTH_USER);

        try {
            switch (action == null ? VIEW : action) {
                case CREATE:
                    if(aUser!=null && aUser.getRole()==User.Role.ADMIN) {
                        req.setAttribute(A_EVENT, new Event(LocalDate.parse(date)));
                        req.setAttribute(A_MOVIES, movieService.getActiveMovies());
                        req.setAttribute(A_ACTION, action);
                        req.getRequestDispatcher(EVENT_FORM_JSP).forward(req, resp);
                    }
                    break;
                case DELETE:
                    if(aUser!=null && aUser.getRole()==User.Role.ADMIN) {
                        service.delete(Integer.parseInt(eid));
                        resp.sendRedirect(SCHEDULE + getBackReference(req));
                    }
                    break;
                case VIEW:
                    int eventId = Integer.parseInt(eid);
                    Event event = service.getEvent(eventId);
                    if(event.getDate().isBefore(LocalDate.now()) || event.getDate().isAfter(LocalDate.now().plusDays(7))) {
                        logger.warn("requested date is out of allowed interval");
                        resp.setStatus(404);
                    } else {
                        req.setAttribute(A_EVENT, event);

                        int rows = Integer.parseInt(req.getServletContext().getInitParameter(A_HALL_ROWS));
                        int seats = Integer.parseInt(req.getServletContext().getInitParameter(A_HALL_SEATS));
                        Hall hall = new Hall(rows, seats);
                        hall.place(tService.getAllTicketsByEventId(eventId));
                        validatePreorders(req);
                        hall.place((List<Ticket>) req.getSession().getAttribute(A_TICKETS));
                        req.setAttribute(A_HALL, hall);
                        req.getRequestDispatcher(EVENT_VIEW_JSP).forward(req, resp);
                    }
                    break;
                default:
                    resp.sendRedirect(SCHEDULE);
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

    private void validatePreorders(HttpServletRequest req) {
        List<Ticket> tickets = (List<Ticket>) req.getSession().getAttribute(A_TICKETS);
        // if user comes to another event page all pre-ordered tickets should be removed from cache (session)
        if(tickets!=null && !tickets.isEmpty() && tickets.get(0).getEventId()!=Integer.parseInt(req.getParameter(A_EID))) {
            logger.info("Invalidation pre-orders: {} removed", tickets.size());
            tickets.clear();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mid = req.getParameter(A_MID);
        String sid = req.getParameter(A_SID);
        String date = req.getParameter(A_DATE);

        Event event = null;
        try{
            event = new Event(null, LocalDate.parse(date),
                    new Seance(Integer.parseInt(sid)), new Movie(Integer.parseInt(mid)));
            service.save(event);
            resp.sendRedirect(SCHEDULE + getBackReference(req));
        } catch (DateTimeParseException | NumberFormatException e) {
            logger.warn("Parsing error", e.getMessage());
            resp.setStatus(404);
        } catch (DuplicateEntityException de) {
            logger.warn(de);
            req.setAttribute(A_DUPLICATE, true);
            req.setAttribute(A_MOVIES, movieService.getActiveMovies());
            req.setAttribute(A_EVENT, event);
            req.getRequestDispatcher(EVENT_FORM_JSP).forward(req, resp);
        } catch (DBException e) {
            logger.error(e);
            resp.setStatus(500);
        }
    }
}
