package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.model.Event;
import com.elesson.pioneer.service.EventService;
import com.elesson.pioneer.service.EventServiceImpl;
import com.elesson.pioneer.service.MovieService;
import com.elesson.pioneer.service.MovieServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class EventServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(EventServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        EventService service = EventServiceImpl.getEventService();
        MovieService mService = MovieServiceImpl.getMovieService();

        String action = req.getParameter("action");
        String eid = req.getParameter("eid");
        String date = req.getParameter("date");

        switch (action == null ? "view" : action) {
            case "create":
                req.setAttribute("event", new Event(LocalDate.parse(date)));
                req.setAttribute("action", action);
                req.setAttribute("movies",mService.getMovies());
                req.getRequestDispatcher("jsp/eventForm.jsp").forward(req, resp);
                break;
            case "delete":
                Event e = service.delete(Integer.parseInt(eid));
                resp.sendRedirect("schedule?date=" + e.getDate().toString());
                break;
            case "view":
                req.setAttribute("event", service.getEvent(Integer.parseInt(eid)));
                req.setAttribute("action", action);
                req.getRequestDispatcher("jsp/eventView.jsp").forward(req, resp);
                break;
            default:
                resp.sendRedirect("schedule");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost");
    }
}
