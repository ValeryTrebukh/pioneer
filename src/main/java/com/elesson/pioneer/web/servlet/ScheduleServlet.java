package com.elesson.pioneer.web.servlet;


import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.service.EventService;
import com.elesson.pioneer.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static com.elesson.pioneer.web.util.Constants.*;


/**
 * The {@code ScheduleServlet} class purpose is to form a list of {@code Event} class objects
 * representing a daily schedule of planned Movies to be shown in the cinema.
 * Possible URL attributes:
 *      date=somedate - somedate will be parsed to LocalDate and the records for specified
 *          data will be returned.
 * Redirects to error page if some exception comes from DAO layer.
 */
public class ScheduleServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ScheduleServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EventService service = ServiceFactory.getEventService();

        String date = req.getParameter(A_DATE);
        try {
            LocalDate localDate = date==null ? LocalDate.now() : LocalDate.parse(date);
            if(localDate.isBefore(LocalDate.now()) || localDate.isAfter(LocalDate.now().plusDays(7))) {
                logger.warn("requested date is out of allowed interval");
                resp.setStatus(404);
            } else {
                req.setAttribute(A_EVENTS, service.getEvents(localDate));
                req.setAttribute(A_NEXT_WEEK, getNextWeek());
                req.setAttribute(A_DATE, localDate);

                req.getRequestDispatcher(SCHEDULE_JSP).forward(req, resp);
            }
        } catch (DateTimeParseException e) {
            logger.warn("Incorrect date input");
            resp.setStatus(404);
        }  catch (DBException e) {
            logger.error(e);
            resp.setStatus(500);
        }
    }

    private List<LocalDate> getNextWeek() {
        List<LocalDate> nextWeek = new ArrayList<>();
        for(int i = 0; i < 7; i++) {
            nextWeek.add(LocalDate.now().plusDays(i));
        }
        return nextWeek;
    }
}