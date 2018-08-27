package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.dao.exception.DuplicateEntityException;
import com.elesson.pioneer.model.User;
import com.elesson.pioneer.service.UserService;
import com.elesson.pioneer.service.UserServiceImpl;
import com.elesson.pioneer.service.exception.NotFoundEntityException;
import com.elesson.pioneer.service.util.Paginator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The {@code UserServlet} class provides one of main functional for the Admins (CRUD).
 *      - View the list of users.
 *      - Create new user.
 *      - Edit created user data.
 *      - Delete the users from database.
 * Redirects to error page if some exception comes from DAO or service layer.
 */
public class UserServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        UserService service = UserServiceImpl.getUserService();
        String action = req.getParameter("action");

        try {
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
                    resp.sendRedirect("users" + getAdd(req));
                    break;
                case "all":
                    Paginator<User> paginator = new Paginator<>();
                    List<User> users = service.getAll();
                    int page = req.getParameter("page")!=null ? Integer.parseInt(req.getParameter("page")) : 1;
                    int pagesCount = paginator.getPageCount(users);
                    page = page > pagesCount ? pagesCount : page < 1 ? 1 : page;
                    req.setAttribute("users", paginator.getPage(users, page));
                    req.setAttribute("page", page);
                    req.setAttribute("pagesCount", paginator.getPageCount(users));
                    req.getRequestDispatcher("jsp/users.jsp").forward(req, resp);
                    break;
                default:
                    req.getRequestDispatcher("jsp/users.jsp").forward(req, resp);
                    break;
            }
        } catch (NumberFormatException | NotFoundEntityException e) {
            logger.warn(e.getMessage());
            resp.setStatus(404);
        } catch (DBException e) {
            logger.error(e);
            resp.setStatus(500);
        }
    }

    private String getAdd(HttpServletRequest req) {
        String referer = req.getHeader("referer");
        return referer.lastIndexOf("?") != -1 ? referer.substring(referer.lastIndexOf("?")) : "";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String regName = req.getParameter("name");
        String regEmail = req.getParameter("email");
        String regPassword = req.getParameter("password");
        String regRole = req.getParameter("role")!=null ? req.getParameter("role") : "CLIENT";
        UserService service = UserServiceImpl.getUserService();

        try {
            User user = new User(regName, regEmail, regPassword, User.Role.valueOf(regRole));
            if (req.getParameter("userid").isEmpty()) {
                service.create(user);
            }
            else {
                user.setId(Integer.parseInt(req.getParameter("userid")));
                service.update(user);
            }
            resp.sendRedirect("users");
        } catch (IllegalArgumentException e) {
            logger.warn("Parsing error", e.getMessage());
            resp.setStatus(404);
        } catch (DuplicateEntityException de) {
            logger.warn(de);
            req.setAttribute("duplicate", true);
            User user = new User(regName, regEmail, null, null);
            if(req.getParameter("userid")!=null) {
                user.setId(Integer.parseInt(req.getParameter("userid")));
            }
            req.setAttribute("user", user);
            req.getRequestDispatcher("jsp/userForm.jsp").forward(req, resp);
        } catch (DBException e) {
            logger.error(e);
            resp.setStatus(500);
        }
    }
}
