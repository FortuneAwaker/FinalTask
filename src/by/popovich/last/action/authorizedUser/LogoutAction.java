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

public class LogoutAction extends AuthorizedUserAction {
    /**
     * Logger.
     */
    private static Logger LOGGER = LogManager.getLogger(LogoutAction.class);

    @Override
    public Forward executeAction(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
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
        LOGGER.info(String.format("User \"%s\" is logged out", currentUser.getLogin()));
        request.getSession(false).invalidate();
        return new Forward("/index.html");
    }
}
