package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.model.Movie;
import com.elesson.pioneer.service.MovieService;
import com.elesson.pioneer.service.MovieServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MovieManagerServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(MovieManagerServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        MovieService service = MovieServiceImpl.getMovieService();

        String action = req.getParameter("action");
        switch (action == null ? "all" : action) {
            case "edit":
            case "create":
                final Movie movie = "create".equals(action) ?
                        new Movie() : service.get(Integer.parseInt(req.getParameter("movieid")));
                logger.debug("movie received");
                req.setAttribute("movie", movie);
                req.getRequestDispatcher("jsp/movieForm.jsp").forward(req, resp);
                break;
            case "delete":
                service.delete(Integer.parseInt(req.getParameter("movieid")));
                resp.sendRedirect("movies");
                break;
            case "all":
                List<Movie> movies = service.getMovies();

                req.setAttribute("movies", movies);
                req.getRequestDispatcher("jsp/movies.jsp").forward(req, resp);
                break;
            default:
                req.getRequestDispatcher("jsp/movies.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String genre = req.getParameter("genre");
        String duration = req.getParameter("duration");
        String year = req.getParameter("year");

        Movie movie = new Movie(name, genre, Integer.parseInt(duration), Integer.parseInt(year));

        MovieService service = MovieServiceImpl.getMovieService();
        if (req.getParameter("movieid").isEmpty()) {
            service.create(movie);
        }
        else {
            movie.setId(Integer.parseInt(req.getParameter("movieid")));
            service.update(movie);
        }
        resp.sendRedirect("movies");
    }
}
