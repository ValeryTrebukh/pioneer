package com.elesson.pioneer.web.filter;

import com.elesson.pioneer.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminAccessFilter implements Filter {

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
            resp.setStatus(403);
        }
    }

    @Override
    public void destroy() {

    }
}
