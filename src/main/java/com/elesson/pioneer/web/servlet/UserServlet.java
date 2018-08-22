package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.model.User;
import com.elesson.pioneer.service.UserService;
import com.elesson.pioneer.service.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        UserService service = UserServiceImpl.getUserService();

        String action = req.getParameter("action");
        switch (action == null ? "all" : action) {
            case "edit":
            case "create":
                final User user = "create".equals(action) ?
                        new User() : service.get(Integer.parseInt(req.getParameter("userid")));
                req.setAttribute("user", user);
                req.getRequestDispatcher("jsp/userForm.jsp").forward(req, resp);
                break;
            case "delete":
                service.delete(Integer.parseInt(req.getParameter("userid")));
                resp.sendRedirect("users");
                break;
            case "all":
                List<User> users = service.getAll();

                req.setAttribute("users", users);
                req.getRequestDispatcher("jsp/users.jsp").forward(req, resp);
                break;
            default:
                req.getRequestDispatcher("jsp/users.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String regName = req.getParameter("name");
        String regEmail = req.getParameter("email");
        String regPassword = req.getParameter("password");
        String regRole = req.getParameter("role")!=null?req.getParameter("role"):"CLIENT";

        User user = new User(regName, regEmail, regPassword, User.Role.valueOf(regRole));

        UserService service = UserServiceImpl.getUserService();
        if (req.getParameter("userid").isEmpty()) {
            service.create(user);
        }
        else {
            user.setId(Integer.parseInt(req.getParameter("userid")));
            service.update(user);
        }
        resp.sendRedirect("users");
    }
}
