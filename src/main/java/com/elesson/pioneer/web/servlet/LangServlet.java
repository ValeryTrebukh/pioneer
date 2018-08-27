package com.elesson.pioneer.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The {@code LangServlet} class purpose is to provide multi-language interface support
 * and switch between available languages
 */
public class LangServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        resp.sendRedirect(req.getHeader("referer"));
        session.setAttribute("language", req.getParameter("language"));
    }
}
