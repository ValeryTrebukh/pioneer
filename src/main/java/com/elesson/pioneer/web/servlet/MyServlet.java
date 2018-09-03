package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.model.User;
import com.elesson.pioneer.service.ServiceFactory;
import com.elesson.pioneer.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.elesson.pioneer.web.util.Constants.*;


/**
 * Shows list of Tickets owned by specific User.
 * Available only for authorized Users.
 */
public class MyServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(MyServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User aUser = (User)session.getAttribute(A_AUTH_USER);
        TicketService service = ServiceFactory.getTicketService();

        if(aUser!=null) {
            try {
                req.setAttribute(A_TICKETS, service.getAllTicketsByUserId(aUser.getId()));
            } catch (DBException e) {
                logger.error(e);
                resp.setStatus(500);
            }
            req.getRequestDispatcher(MY_TICKETS_JSP).forward(req, resp);
        } else {
            resp.setStatus(404);
        }
    }
}
