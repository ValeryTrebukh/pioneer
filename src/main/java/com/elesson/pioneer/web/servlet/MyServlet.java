package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.model.User;
import com.elesson.pioneer.service.TicketService;
import com.elesson.pioneer.service.TicketServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        int uid = ((User)session.getAttribute("authUser")).getId();
        TicketService service = TicketServiceImpl.getTicketService();

        req.setAttribute("tickets", service.getAllTicketsByUserId(uid));

        req.getRequestDispatcher("jsp/myTickets.jsp").forward(req, resp);

    }
}
