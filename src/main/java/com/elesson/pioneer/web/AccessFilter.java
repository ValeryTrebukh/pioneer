package com.elesson.pioneer.web;

import com.elesson.pioneer.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;

        HttpSession session = req.getSession(true);
        User authUser = (User)session.getAttribute("authUser");
        if(authUser!=null && authUser.getRole()==User.Role.ADMIN) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            req.setAttribute("message", "access");
            req.getRequestDispatcher("/jsp/errorPage.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}
