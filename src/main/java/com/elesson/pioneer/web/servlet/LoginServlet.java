package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.model.User;
import com.elesson.pioneer.service.*;
import com.elesson.pioneer.service.exception.NotFoundEntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.elesson.pioneer.service.util.Security.encrypt;
import static com.elesson.pioneer.web.util.Constants.*;

/**
 * The {@code LoginServlet} class purpose is to validate user's credentials.
 * Invalidates the session and creates new one. After that stores user data into session.
 * Redirects to error page if some exception comes from DAO layer.
 */
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authEmail = req.getParameter(A_AUTH_USER_EMAIL);

        try{
            UserService userService = ServiceFactory.getUserService();
            User aUser = userService.getByEmail(authEmail);
            if(aUser!=null && aUser.getPassword().equals(encrypt(req.getParameter(A_AUTH_USER_PASS)))) {
                HttpSession session = req.getSession();
                session.invalidate();
                session = req.getSession();
                session.setAttribute(A_AUTH_USER, aUser);
                logger.info("{} logged in", aUser.getName());
                resp.sendRedirect(SCHEDULE);
            } else {
                throw new NotFoundEntityException("Wrong password");
            }
        } catch (NotFoundEntityException e) {
            logger.warn(e.getMessage());
            req.setAttribute(A_ERR, "password");
            req.getRequestDispatcher(LOGIN_JSP).forward(req, resp);
        } catch (DBException e) {
            logger.error(e);
            resp.setStatus(500);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LOGIN_JSP).forward(req, resp);
    }
}
