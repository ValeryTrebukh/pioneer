package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.model.User;
import com.elesson.pioneer.service.UserService;
import com.elesson.pioneer.service.exception.NotFoundEntityException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServletTest {

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher requestDispatcher;
    @Mock private UserService userService;
    @Mock private HttpSession session;
    @Mock private User aUser;

    @InjectMocks
    private LoginServlet loginServlet = new LoginServlet();

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("authUserPass")).thenReturn("password");
        when(request.getParameter("authUserEmail")).thenReturn("test@email");
        when(userService.getByEmail("test@email")).thenReturn(aUser);
    }

    @Test
    public void doGetTest() throws Exception {
        when(request.getRequestDispatcher("jsp/login.jsp")).thenReturn(requestDispatcher);

        loginServlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPost_shouldProceedWithCorrectData() throws Exception {
        when(aUser.getPassword()).thenReturn("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");

        loginServlet.doPost(request, response);

        verify(session).invalidate();
        verify(session).setAttribute("authUser", aUser);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void doPost_shouldRedirectToItselfForIncorrectPassword() throws Exception {
        when(aUser.getPassword()).thenReturn("w");
        when(request.getRequestDispatcher("jsp/login.jsp")).thenReturn(requestDispatcher);

        loginServlet.doPost(request, response);

        verify(request).setAttribute("error", "password");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPost_shouldRedirectToItselfForIncorrectUser() throws Exception {
        when(aUser.getPassword()).thenThrow(new NotFoundEntityException("text"));
        when(request.getRequestDispatcher("jsp/login.jsp")).thenReturn(requestDispatcher);

        loginServlet.doPost(request, response);

        verify(request).setAttribute("error", "password");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPost_shouldReturn500ForDBException() throws Exception {
        when(aUser.getPassword()).thenThrow(new DBException("text"));

        loginServlet.doPost(request, response);

        verify(response).setStatus(500);
    }

}