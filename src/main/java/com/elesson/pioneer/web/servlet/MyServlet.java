package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.dao.exception.DBException;
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


/**
 * Shows list of Tickets owned by specific User.
 * Available only for authorized Users.
 */
public class MyServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(MyServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User aUser = (User)session.getAttribute("authUser");
        TicketService service = TicketServiceImpl.getTicketService();

        if(aUser!=null) {
            try {
                req.setAttribute("tickets", service.getAllTicketsByUserId(aUser.getId()));
            } catch (DBException e) {
                logger.error(e);
                resp.setStatus(500);
            }
            req.getRequestDispatcher("jsp/myTickets.jsp").forward(req, resp);
        } else {
            resp.setStatus(403);
        }
    }
}
