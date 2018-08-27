package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Deletes all user's credentials by invalidating the session.
 */
public class LogoutServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User aUser = (User)session.getAttribute("authUser");
        if(aUser!=null) {
            logger.info("{} logget out", aUser.getName());
        }
        session.invalidate();
        req.getRequestDispatcher("jsp/login.jsp").forward(req, resp);
    }
}
