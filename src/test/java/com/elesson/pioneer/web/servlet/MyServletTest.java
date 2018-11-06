package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.model.Ticket;
import com.elesson.pioneer.model.User;
import com.elesson.pioneer.service.TicketService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MyServletTest {

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private TicketService tService;
    @Mock private User aUser;
    @Mock private HttpSession session;
    @Mock private RequestDispatcher requestDispatcher;

    @InjectMocks
    private MyServlet myServlet = new MyServlet();

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("authUser")).thenReturn(aUser);
    }

    @Test
    public void shouldProceedForCorrectData() throws Exception {
        when(aUser.getId()).thenReturn(1);
        List<Ticket> ticketList = new ArrayList<>();
        when(tService.getAllTicketsByUserId(1)).thenReturn(ticketList);
        when(request.getRequestDispatcher("jsp/myTickets.jsp")).thenReturn(requestDispatcher);

        myServlet.doGet(request, response);

        verify(request).setAttribute("tickets", ticketList);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldReturn404ForNoUserInSession() throws Exception {
        when(session.getAttribute("authUser")).thenReturn(null);

        myServlet.doGet(request, response);

        verify(response).setStatus(404);
    }

    @Test
    public void doGet_shouldReturn500ForDBException() throws Exception {
        when(tService.getAllTicketsByUserId(anyInt())).thenThrow(new DBException(""));
        when(request.getRequestDispatcher("jsp/myTickets.jsp")).thenReturn(requestDispatcher);

        myServlet.doGet(request, response);

        verify(response).setStatus(500);
        verify(requestDispatcher).forward(request, response);
    }

}