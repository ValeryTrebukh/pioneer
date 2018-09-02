package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.model.User;
import com.elesson.pioneer.service.*;
import com.elesson.pioneer.service.exception.NotFoundEntityException;
import com.elesson.pioneer.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.elesson.pioneer.service.util.Security.encrypt;

/**
 * The {@code LoginServlet} class purpose is to validate user's credentials.
 * Invalidates the session and creates new one. After that stores user data into session.
 * Redirects to error page if some exception comes from DAO layer.
 */
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authEmail = req.getParameter("authUserEmail");

        try{
            UserService userService = UserServiceImpl.getUserService();
            User aUser = userService.getByEmail(authEmail);
            if(aUser!=null && aUser.getPassword().equals(encrypt(req.getParameter("authUserPass")))) {
                HttpSession session = req.getSession();
                session.invalidate();
                session = req.getSession();
                session.setAttribute("authUser", aUser);
                logger.info("{} logged in", aUser.getName());
                resp.sendRedirect("schedule");
            } else {
                throw new NotFoundEntityException("Wrong password");
            }
        } catch (NotFoundEntityException e) {
            logger.warn(e.getMessage());
            req.setAttribute("error", "password");
            req.getRequestDispatcher("jsp/login.jsp").forward(req, resp);
        } catch (DBException e) {
            logger.error(e);
            resp.setStatus(500);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsp/login.jsp").forward(req, resp);
    }
}
