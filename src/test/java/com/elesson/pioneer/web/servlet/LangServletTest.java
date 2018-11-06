package com.elesson.pioneer.web.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LangServletTest {

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;

    @InjectMocks
    private LangServlet langServlet = new LangServlet();

    @Test
    public void doGetTest() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(request.getHeader("referer")).thenReturn("back");
        when(request.getParameter("language")).thenReturn("RU");

        langServlet.doGet(request, response);

        verify(response).sendRedirect("back");
        verify(session).setAttribute("language", "RU");
    }
}