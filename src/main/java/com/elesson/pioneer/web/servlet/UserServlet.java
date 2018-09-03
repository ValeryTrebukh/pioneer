package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.dao.exception.DuplicateEntityException;
import com.elesson.pioneer.model.User;
import com.elesson.pioneer.service.UserService;
import com.elesson.pioneer.service.impl.UserServiceImpl;
import com.elesson.pioneer.service.exception.NotFoundEntityException;
import com.elesson.pioneer.service.util.Paginator;
import com.elesson.pioneer.service.util.UserCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.elesson.pioneer.web.util.Constants.*;
import static com.elesson.pioneer.web.util.Helper.getBackReference;

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
        String action = req.getParameter(A_ACTION);

        try {
            switch (action==null?"":action) {
                case EDIT:
                case CREATE:
                    final User user = CREATE.equals(action) ?
                            new User() : service.get(Integer.parseInt(req.getParameter(A_UID)));
                    req.setAttribute(A_USER, user);
                    req.getRequestDispatcher(USER_FORM_JSP).forward(req, resp);
                    break;
                case DELETE:
                    service.delete(Integer.parseInt(req.getParameter(A_UID)));
                    resp.sendRedirect(USERS + getBackReference(req));
                    break;
                default:
                    setPageDataToRequest(req);
                    req.getRequestDispatcher(USERS_JSP).forward(req, resp);
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

    private void setPageDataToRequest(HttpServletRequest req) {
        int pageSize = Integer.parseInt(req.getServletContext().getInitParameter(A_PAGE_SIZE));
        Paginator<User> paginator = new Paginator<>(pageSize);
        List<User> users = UserCache.getUsers();
        int currentPage = req.getParameter(A_PAGE)!=null ? Integer.parseInt(req.getParameter(A_PAGE)) : 1;
        int pagesCount = paginator.getPageCount(users);
        currentPage = currentPage > pagesCount ? pagesCount : currentPage < 1 ? 1 : currentPage;
        req.setAttribute(A_USERS, paginator.getPage(users, currentPage));
        req.setAttribute(A_PAGE, currentPage);
        req.setAttribute(A_PAGES_COUNT, paginator.getPageCount(users));
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String regName = req.getParameter(A_REG_NAME);
        String regEmail = req.getParameter(A_REG_EMAIL);
        String regPassword = req.getParameter(A_REG_PASS);
        String regRole = req.getParameter(A_ROLE)!=null ? req.getParameter(A_ROLE) : User.Role.CLIENT.toString();
        UserService service = UserServiceImpl.getUserService();

        try {
            User user = new User(regName, regEmail, regPassword, User.Role.valueOf(regRole));
            if (req.getParameter(A_UID).isEmpty()) {
                service.create(user);
            }
            else {
                user.setId(Integer.parseInt(req.getParameter(A_UID)));
                service.update(user);
            }
            resp.sendRedirect(USERS);
        } catch (IllegalArgumentException e) {
            logger.warn("Parsing error", e.getMessage());
            resp.setStatus(404);
        } catch (DuplicateEntityException de) {
            logger.warn(de);
            req.setAttribute(A_DUPLICATE, true);
            User user = new User(regName, regEmail, null, null);
            if(req.getParameter(A_UID)!=null) {
                user.setId(Integer.parseInt(req.getParameter(A_UID)));
            }
            req.setAttribute(A_USER, user);
            req.getRequestDispatcher(USER_FORM_JSP).forward(req, resp);
        } catch (DBException e) {
            logger.error(e);
            resp.setStatus(500);
        }
    }
}
