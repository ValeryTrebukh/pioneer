package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.dao.exception.DuplicateEntityException;
import com.elesson.pioneer.model.User;
import com.elesson.pioneer.service.UserService;
import com.elesson.pioneer.service.UserServiceImpl;
import com.elesson.pioneer.web.util.UserDataValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(RegistrationServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsp/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String regName = req.getParameter("regName");
        String regEmail = req.getParameter("regEmail");
        String regPassword = req.getParameter("regPass");
        String confPassword = req.getParameter("confPass");

        if(UserDataValidation.hasError(req, regName, regEmail, regPassword, confPassword)) {
            req.getRequestDispatcher("jsp/registration.jsp").forward(req, resp);
        } else {
            try {
                UserService userService = UserServiceImpl.getUserService();
                User user = new User(regName, regEmail, regPassword, User.Role.CLIENT);
                userService.create(user);
                HttpSession session = req.getSession();
                session.invalidate();
                session = req.getSession();
                session.setAttribute("authUser", userService.getByEmail(regEmail));
                logger.info("{} logged in", user.getName());
                resp.sendRedirect("schedule");
            } catch (DuplicateEntityException de) {
                logger.warn(de.getMessage());
                req.setAttribute("duplicate", true);
                req.getRequestDispatcher("jsp/registration.jsp").forward(req, resp);
            } catch (DBException e) {
                resp.setStatus(500);
            }
        }
    }
}
