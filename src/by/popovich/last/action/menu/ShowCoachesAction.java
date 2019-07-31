package by.popovich.last.action.menu;

import by.popovich.last.action.Action;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Person;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.UserInfoService;
import by.popovich.last.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShowCoachesAction extends Action {
    /**
     * Logger for creation notes to some appender.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ShowExercisesAction.class);

    @Override
    public Forward executeAction(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        HttpSession session = request.getSession(true);
        String lang = (String) session.getAttribute("lang");
        if (lang == null) {
            lang = "ru";
            session.setAttribute("lang", lang);
        }
        request.setAttribute("lang", lang);
        Locale locale = new Locale(lang);
        Config.set(request, Config.FMT_LOCALE, locale);
        UserService service = serviceFactory.getService(UserService.class);
        UserInfoService infoService = serviceFactory.getService((UserInfoService.class));
        List<Person> coaches = new ArrayList<>();
        for (User user:service.readAll()
             ) {
            if (user.getRole().getIdentity() == 1) {
                coaches.add(infoService.readById(user.getIdentity()));
            }
        }

        if (coaches != null) {
            if (request.getAttribute("listOfCoaches") == null) {
                request.setAttribute("listOfCoaches", coaches);
                LOGGER.info("List of coaches was shown successfully");
            }
        }
        return new Forward("/menu/coaches.jsp", false);
    }
}
