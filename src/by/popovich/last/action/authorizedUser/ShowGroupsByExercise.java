package by.popovich.last.action.authorizedUser;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Group;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.GroupService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

public class ShowGroupsByExercise extends AuthorizedUserAction {
    /**
     * Logger.
     */
    private static Logger LOGGER = LogManager.getLogger(ShowGroupsByExercise.class);

    @Override
    public Forward executeAction(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("authorizedUser");
        if (currentUser != null) {
            String lang = (String) session.getAttribute("lang");
            if (lang == null) {
                lang = "ru";
                session.setAttribute("lang", lang);
            }
            request.setAttribute("lang", lang);
            Locale locale = new Locale(lang);
            Config.set(request, Config.FMT_LOCALE, locale);
            String exercise = request.getParameter("exercise");
            GroupService service = serviceFactory.getService(GroupService.class);
            List<Group> groups = service.readGroupsByTypeName(exercise);
            if (groups != null) {
                session.setAttribute("exerciseName", exercise);
                session.setAttribute("groupsByExercise", groups);
                LOGGER.info("Группы по упражнению были показаны.");
                return new Forward("/authorized_user/groupsByExercise.jsp", false);
            } else {
                LOGGER.info("Группы не найдены!");
                request.setAttribute("message", "Группы не найдены!");
            }
        } else {
            request.setAttribute("message",
                    "Нужно войти, чтобы просматривать эту страницу!");
            return new Forward("/index.html");
        }
        return new Forward("/index.html");
    }
}
