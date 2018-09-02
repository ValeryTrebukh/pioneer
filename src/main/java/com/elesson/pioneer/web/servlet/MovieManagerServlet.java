package com.elesson.pioneer.web.servlet;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.model.Movie;
import com.elesson.pioneer.service.MovieService;
import com.elesson.pioneer.service.impl.MovieServiceImpl;
import com.elesson.pioneer.service.exception.NotFoundEntityException;
import com.elesson.pioneer.service.util.MovieCache;
import com.elesson.pioneer.service.util.Paginator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.elesson.pioneer.web.util.Helper.getBackReference;

/**
 * The {@code MovieManagerServlet} class provides one of main functional for the Admins (CRUD).
 *      - View the list of movies.
 *      - Create new movie.
 *      - Edit created movie data.
 *      - Delete the movies from database.
 * Redirects to error page if some exception comes from DAO or service layer.
 */
public class MovieManagerServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(MovieManagerServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        MovieService service = MovieServiceImpl.getMovieService();

        String action = req.getParameter("action");
        String mid = req.getParameter("mid");

        try {
            switch (action==null?"default":action) {
                case "edit":
                case "create":
                    final Movie movie = "create".equals(action) ?
                            new Movie() : service.get(Integer.parseInt(mid));
                    logger.debug("Movie obtained");
                    req.setAttribute("movie", movie);
                    req.getRequestDispatcher("jsp/movieForm.jsp").forward(req, resp);
                    break;
                case "delete":
                    service.delete(Integer.parseInt(mid));
                    logger.info("Movie deleted: ", mid);
                    resp.sendRedirect("movies"+ getBackReference(req));
                    break;
                default:
                    setPageDataToRequest(req);
                    req.getRequestDispatcher("jsp/movies.jsp").forward(req, resp);
                    break;
            }
        } catch (NumberFormatException | NotFoundEntityException e) {
            logger.warn(e.getMessage());
            resp.setStatus(404);
        } catch (DBException e) {
            logger.error(e);
            resp.setStatus(500);
        }
    }

    private void setPageDataToRequest(HttpServletRequest req) {
        int pageSize = Integer.parseInt(req.getServletContext().getInitParameter("pageSize"));
        Paginator<Movie> paginator = new Paginator<>(pageSize);
        List<Movie> movies = MovieCache.getMovies();
        String sPage = req.getParameter("page");
        int page = sPage !=null ? Integer.parseInt(sPage) : 1;
        int pagesCount = paginator.getPageCount(movies);
        page = page > pagesCount ? pagesCount : page < 1 ? 1 : page;
        req.setAttribute("movies", paginator.getPage(movies, page));
        req.setAttribute("page", page);
        req.setAttribute("pagesCount", paginator.getPageCount(movies));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String genre = req.getParameter("genre");
        String duration = req.getParameter("duration");
        String year = req.getParameter("year");
        String active = req.getParameter("status");

        MovieService service = MovieServiceImpl.getMovieService();

        try {
            Movie movie = new Movie(name, genre, Integer.parseInt(duration), Integer.parseInt(year), Boolean.valueOf(active));

            if (req.getParameter("mid").isEmpty()) {
                service.create(movie);
            }
            else {
                movie.setId(Integer.parseInt(req.getParameter("mid")));
                service.update(movie);
            }
            resp.sendRedirect("movies");
        } catch (DBException e) {
            logger.error(e);
            resp.setStatus(500);
        }
    }
}
