package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.model.*;
import com.elesson.pioneer.service.EventService;
import com.elesson.pioneer.service.MovieService;
import com.elesson.pioneer.service.TicketService;
import com.elesson.pioneer.service.exception.NotFoundEntityException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void createAction_shouldNotCreateForNullUserInSession() throws Exception {
        when(request.getParameter("action")).thenReturn("create");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("authUser")).thenReturn(null);

        eventServlet.doGet(request, response);

        verify(request).getParameter("action");
        verify(request).getParameter("eid");
        verify(request).getParameter("date");
        verify(request).getSession();
        verifyNoMoreInteractions(request);
    }

    @Test
    public void createAction_shouldNotCreateForNonAdminUserInSession() throws Exception {
        when(request.getParameter("action")).thenReturn("create");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("authUser")).thenReturn(aUser);
        when(aUser.getRole()).thenReturn(User.Role.CLIENT);

        eventServlet.doGet(request, response);

        verify(request).getParameter("action");
        verify(request).getParameter("eid");
        verify(request).getParameter("date");
        verify(request).getSession();
        verifyNoMoreInteractions(request);
    }

    @Test
    public void createAction_shouldRedirectToCreateForm() throws Exception {
        when(request.getParameter("action")).thenReturn("create");
        when(request.getParameter("date")).thenReturn("2018-09-13");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("authUser")).thenReturn(aUser);
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
    public void deleteAction_shouldCallServiceDeleteMethod() throws Exception {
        when(request.getParameter("eid")).thenReturn("5");
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("authUser")).thenReturn(aUser);
        when(aUser.getRole()).thenReturn(User.Role.ADMIN);
        when(request.getHeader("referer")).thenReturn("schedule?");

        eventServlet.doGet(request, response);

        verify(service).delete(5);
        verify(response).sendRedirect("schedule?");
    }

    @Test
    public void viewAction_shouldReturn404ForPastDate() throws Exception {
        when(request.getParameter("eid")).thenReturn("5");
        when(request.getParameter("action")).thenReturn("view");
        when(request.getParameter("date")).thenReturn("2018-09-13");
        when(service.getEvent(5)).thenReturn(new Event(LocalDate.now().minusDays(2)));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("authUser")).thenReturn(aUser);

        eventServlet.doGet(request, response);

        verify(response).setStatus(404);
    }

    @Test
    public void viewAction_shouldReturn404ForAnyDateAbove7Days() throws Exception {
        when(request.getParameter("eid")).thenReturn("5");
        when(request.getParameter("action")).thenReturn("view");
        when(request.getParameter("date")).thenReturn("fake data");
        when(service.getEvent(5)).thenReturn(new Event(LocalDate.now().plusDays(8)));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("authUser")).thenReturn(aUser);

        eventServlet.doGet(request, response);

        verify(response).setStatus(404);
    }

    @Test
    public void viewAction_shouldRedirectToViewPageForCorrectDate() throws Exception {
        when(request.getParameter("eid")).thenReturn("5");
        when(request.getParameter("action")).thenReturn("view");
        when(request.getParameter("date")).thenReturn("2018-09-13");
        when(service.getEvent(5)).thenReturn(new Event(LocalDate.now()));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("authUser")).thenReturn(aUser);
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter(anyString())).thenReturn("5");
        when(tService.getAllTicketsByEventId(anyInt())).thenReturn(new ArrayList<>());
        when(session.getAttribute("tickets")).thenReturn(new ArrayList<>());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        eventServlet.doGet(request, response);

        verify(request).setAttribute(eq("event"), any(Event.class));
        verify(request).setAttribute(eq("hall"), any(Hall.class));
        verify(request).getRequestDispatcher("jsp/eventView.jsp");
    }

    @Test
    public void shouldRedirectToSelfForIncorrectAction() throws Exception {
        when(request.getParameter("action")).thenReturn("default");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("authUser")).thenReturn(aUser);

        eventServlet.doGet(request, response);

        verify(response).sendRedirect("schedule");
    }

    @Test
    public void shouldReturn404ForDateTimeParseException() throws Exception {
        when(request.getParameter("action")).thenReturn("create");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("authUser")).thenReturn(aUser);
        when(aUser.getRole()).thenThrow(new DateTimeParseException("", "", 1));

        eventServlet.doGet(request, response);

        verify(response).setStatus(404);
    }

    @Test
    public void shouldReturn404ForNumberFormatException() throws Exception {
        when(request.getParameter("action")).thenReturn("create");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("authUser")).thenReturn(aUser);
        when(aUser.getRole()).thenThrow(new NumberFormatException());

        eventServlet.doGet(request, response);

        verify(response).setStatus(404);
    }

    @Test
    public void shouldReturn404ForNotFoundEntityException() throws Exception {
        when(request.getParameter("action")).thenReturn("create");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("authUser")).thenReturn(aUser);
        when(aUser.getRole()).thenThrow(new NotFoundEntityException(""));

        eventServlet.doGet(request, response);

        verify(response).setStatus(404);
    }

    @Test
    public void shouldReturn500ForDBException() throws Exception {
        when(request.getParameter("action")).thenReturn("create");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("authUser")).thenReturn(aUser);
        when(aUser.getRole()).thenThrow(new DBException(""));

        eventServlet.doGet(request, response);

        verify(response).setStatus(500);
    }


}