package by.epam.naumovich.film_ordering.command.impl.navigation;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * Performs the command that forwards request and response to the relevant JSP.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenSignUpPage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException {

        setPrevQueryAttributeToSession(request, session, log);
		
		if (isAuthorized(session)) {
			int userId = fetchUserIdFromSession(session);
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.LOG_OUT_FOR_SIGN_UP);
			request.getRequestDispatcher("/Controller?command=open_user_profile&userId=" + userId)
                    .forward(request, response);
		} else {
			request.getRequestDispatcher(JavaServerPageNames.SIGN_UP_PAGE).forward(request, response);
		}
	}
}
