package by.popovich.last.action.authorizedUser;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

/**
 * Log out action class.
 */
public class LogoutAction extends AuthorizedUserAction {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
            .getLogger(LogoutAction.class);

    /**
     * Executes action.
     *
     * @param request  HttpServletRequest.
     * @param response HttpServletResponse
     * @return url to go.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public Forward executeAction(final HttpServletRequest request,
                                 final HttpServletResponse response)
            throws PersistentException {
        HttpSession session = request.getSession();
        String lang = (String) session.getAttribute("lang");
        if (lang == null) {
            lang = "ru";
            session.setAttribute("lang", lang);
        }
        request.setAttribute("lang", lang);
        Locale locale = new Locale(lang);
        Config.set(request, Config.FMT_LOCALE, locale);
        User currentUser = (User) session.getAttribute("authorizedUser");
        LOGGER.info(String.format("User \"%s\" is logged out",
                currentUser.getLogin()));
        request.getSession(false).invalidate();
        return new Forward("/index.html");
    }
}
