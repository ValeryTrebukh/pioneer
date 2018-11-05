package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.dao.exception.*;
import com.elesson.pioneer.model.*;
import com.elesson.pioneer.service.*;
import com.elesson.pioneer.service.exception.NotFoundEntityException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.*;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EventServletTest {

    @Mock private EventService service;
    @Mock private TicketService tService;
    @Mock private MovieService movieService;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private User aUser;
    @Mock private HttpSession session;
    @Mock private RequestDispatcher requestDispatcher;
    @Mock private ServletContext servletContext;

    @InjectMocks
    private EventServlet eventServlet = new EventServlet();

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(servletContext);
        when(request.getParameter("action")).thenReturn("create");
        when(request.getParameter("date")).thenReturn("2018-09-13");
        when(session.getAttribute("authUser")).thenReturn(aUser);
        when(request.getHeader("referer")).thenReturn("schedule?");
        when(request.getParameter("eid")).thenReturn("5");
        when(request.getParameter("mid")).thenReturn("1");
        when(request.getParameter("sid")).thenReturn("1");
    }

    @Test
    public void doGet_createAction_shouldNotCreateForNullUserInSession() throws Exception {
        when(session.getAttribute("authUser")).thenReturn(null);

        eventServlet.doGet(request, response);

        verify(request).getParameter("action");
        verify(request).getParameter("eid");
        verify(request).getParameter("date");
        verify(request).getSession();
        verifyNoMoreInteractions(request);
    }

    @Test
    public void doGet_createAction_shouldNotCreateForNonAdminUserInSession() throws Exception {
        when(aUser.getRole()).thenReturn(User.Role.CLIENT);

        eventServlet.doGet(request, response);

        verify(request).getParameter("action");
        verify(request).getParameter("eid");
        verify(request).getParameter("date");
        verify(request).getSession();
        verifyNoMoreInteractions(request);
    }

    @Test
    public void doGet_createAction_shouldRedirectToCreateForm() throws Exception {
        when(aUser.getRole()).thenReturn(User.Role.ADMIN);
        List<Movie> movieList = new ArrayList<>();
        when(movieService.getActiveMovies()).thenReturn(movieList);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        eventServlet.doGet(request, response);

        verify(request).setAttribute(eq("event"), any(Event.class));
        verify(request).setAttribute("action", "create");
        verify(request).setAttribute("movies", movieList);
        verify(request).getRequestDispatcher("jsp/eventForm.jsp");
    }

    @Test
    public void doGet_deleteAction_shouldCallServiceDeleteMethod() throws Exception {
        when(request.getParameter("action")).thenReturn("delete");
        when(aUser.getRole()).thenReturn(User.Role.ADMIN);

        eventServlet.doGet(request, response);

        verify(service).delete(5);
        verify(response).sendRedirect("schedule?");
    }

    @Test
    public void doGet_viewAction_shouldReturn404ForPastDate() throws Exception {
        when(request.getParameter("action")).thenReturn("view");
        when(service.getEvent(5)).thenReturn(new Event(LocalDate.now().minusDays(2)));

        eventServlet.doGet(request, response);

        verify(response).setStatus(404);
    }

    @Test
    public void doGet_viewAction_shouldReturn404ForAnyDateAbove7Days() throws Exception {
        when(request.getParameter("action")).thenReturn("view");
        when(service.getEvent(5)).thenReturn(new Event(LocalDate.now().plusDays(8)));

        eventServlet.doGet(request, response);

        verify(response).setStatus(404);
    }

    @Test
    public void doGet_viewAction_shouldRedirectToViewPageForCorrectDate() throws Exception {
        when(request.getParameter("action")).thenReturn("view");
        when(service.getEvent(5)).thenReturn(new Event(LocalDate.now()));
        when(servletContext.getInitParameter(anyString())).thenReturn("5");
        when(tService.getAllTicketsByEventId(anyInt())).thenReturn(new ArrayList<>());
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(new Ticket(1, 1, 1));
        when(session.getAttribute("tickets")).thenReturn(ticketList);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        eventServlet.doGet(request, response);

        verify(request).setAttribute(eq("event"), any(Event.class));
        verify(request).setAttribute(eq("hall"), any(Hall.class));
        verify(request).getRequestDispatcher("jsp/eventView.jsp");
    }

    @Test
    public void doGet_shouldRedirectToSelfForIncorrectAction() throws Exception {
        when(request.getParameter("action")).thenReturn("default");

        eventServlet.doGet(request, response);

        verify(response).sendRedirect("schedule");
    }

    @Test
    public void doGet_shouldReturn404ForDateTimeParseException() throws Exception {
        when(aUser.getRole()).thenThrow(new DateTimeParseException("", "", 1));

        eventServlet.doGet(request, response);

        verify(response).setStatus(404);
    }

    @Test
    public void doGet_shouldReturn404ForNumberFormatException() throws Exception {
        when(aUser.getRole()).thenThrow(new NumberFormatException());

        eventServlet.doGet(request, response);

        verify(response).setStatus(404);
    }

    @Test
    public void doGet_shouldReturn404ForNotFoundEntityException() throws Exception {
        when(aUser.getRole()).thenThrow(new NotFoundEntityException(""));

        eventServlet.doGet(request, response);

        verify(response).setStatus(404);
    }

    @Test
    public void doGet_shouldReturn500ForDBException() throws Exception {
        when(aUser.getRole()).thenThrow(new DBException(""));

        eventServlet.doGet(request, response);

        verify(response).setStatus(500);
    }

    @Test
    public void doPost_shouldSaveNewEntity() throws Exception {
        eventServlet.doPost(request, response);

        verify(service).save(any(Event.class));
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void doPost_shouldReturn404ForDateTimeParseException() throws Exception {
        when(request.getHeader("referer")).thenThrow(new DateTimeParseException("", "", 1));

        eventServlet.doPost(request, response);

        verify(response).setStatus(404);
    }

    @Test
    public void doPost_shouldReturn404ForNumberFormatException() throws Exception {
        when(request.getHeader("referer")).thenThrow(new NumberFormatException(""));

        eventServlet.doPost(request, response);

        verify(response).setStatus(404);
    }

    @Test
    public void doPost_shouldRedirectToSelfForDuplicateEntityException() throws Exception {
        when(request.getHeader("referer")).thenThrow(new DuplicateEntityException());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(movieService.getActiveMovies()).thenReturn(Collections.emptyList());

        eventServlet.doPost(request, response);

        verify(request).getRequestDispatcher("jsp/eventForm.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPost_shouldReturn500ForDBException() throws Exception {
        when(request.getHeader("referer")).thenThrow(new DBException(""));

        eventServlet.doPost(request, response);

        verify(response).setStatus(500);
    }
}