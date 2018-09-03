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

import static com.elesson.pioneer.web.util.Constants.*;

/**
 * Deletes all user's credentials by invalidating the session.
 */
public class LogoutServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User aUser = (User)session.getAttribute(A_AUTH_USER);
        if(aUser!=null) {
            logger.info("{} logged out", aUser.getName());
        }
        session.invalidate();
        req.getRequestDispatcher(LOGIN_JSP).forward(req, resp);
    }
}
