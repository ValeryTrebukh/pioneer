package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class LogoutServletTest {

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher requestDispatcher;
    @Mock private HttpSession session;
    @Mock private User aUser;

    @InjectMocks
    private LogoutServlet logoutServlet = new LogoutServlet();

    @Test
    public void doGetTest() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("authUser")).thenReturn(aUser);
        when(request.getRequestDispatcher("jsp/login.jsp")).thenReturn(requestDispatcher);

        logoutServlet.doGet(request, response);

        verify(session).invalidate();
        verify(requestDispatcher).forward(request, response);
    }
}