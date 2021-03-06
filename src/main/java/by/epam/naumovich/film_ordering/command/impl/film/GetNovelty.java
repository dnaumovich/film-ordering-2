package by.epam.naumovich.film_ordering.command.impl.film;

import by.epam.naumovich.film_ordering.bean.Film;
import by.epam.naumovich.film_ordering.bean.Order;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.IOrderService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.GetFilmServiceException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * Performs the command that gets twelve last added films from the service layer and passes it to the relevant JSP.
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class GetNovelty implements Command {

    private final IFilmService filmService;
    private final IOrderService orderService;

    public GetNovelty(IFilmService filmService, IOrderService orderService) {
        this.filmService = filmService;
        this.orderService = orderService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException, ServiceException {

        setPrevQueryAttributeToSession(request, session, log);
        String lang = fetchLanguageFromSession(session);

        try {
            List<Film> filmSet = filmService.getTwelveLastAddedFilms(lang);
            //request.setAttribute(RequestAndSessionAttributes.NOVELTY_LIST, filmSet);
            session.setAttribute(RequestAndSessionAttributes.NOVELTY_LIST, filmSet);

            if (isAuthorized(session) && !isAdmin(session)) {
                int userId = fetchUserIdFromSession(session);
                List<Order> orders = orderService.getAllByUserId(userId);
                List<Integer> orderFilmIDs = orders.stream().map(Order::getFilmId).collect(Collectors.toList());
                session.setAttribute(RequestAndSessionAttributes.USER_ORDER_FILM_IDS, orderFilmIDs);
            }
        } catch (GetFilmServiceException e) {
            log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND,
                    e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(JavaServerPageNames.INDEX_PAGE).forward(request, response);
        }
    }

}
