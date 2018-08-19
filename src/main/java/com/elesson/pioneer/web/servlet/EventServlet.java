package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.model.Event;
import com.elesson.pioneer.model.Hall;
import com.elesson.pioneer.model.Ticket;
import com.elesson.pioneer.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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

        switch (action == null ? "view" : action) {
            case "create":
                req.setAttribute("event", new Event(LocalDate.parse(date)));
                req.setAttribute("action", action);
                req.setAttribute("movies", mService.getActiveMovies());
                req.getRequestDispatcher("jsp/eventForm.jsp").forward(req, resp);
                break;
            case "delete":
                Event e = service.delete(Integer.parseInt(eid));
                resp.sendRedirect("schedule?date=" + e.getDate().toString());
                break;
            case "view":
                req.setAttribute("event", service.getEvent(Integer.parseInt(eid)));
                Hall hall = new Hall(4, 8);
                hall.place(tService.getAllTicketsByEventId(Integer.parseInt(eid)));
                req.setAttribute("hall", hall);
                req.getRequestDispatcher("jsp/eventView.jsp").forward(req, resp);
                break;
            default:
                resp.sendRedirect("schedule");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String mid = req.getParameter("mid");
        String sid = req.getParameter("sid");
        String date = req.getParameter("date");

        Event event = new Event(LocalDate.parse(date));

        EventService service = EventServiceImpl.getEventService();
        service.save(event, Integer.parseInt(mid), Integer.parseInt(sid));
        resp.sendRedirect("schedule?date" + date);
    }
}
