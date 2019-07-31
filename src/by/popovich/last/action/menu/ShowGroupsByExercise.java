package by.popovich.last.action.menu;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.ChangeLanguageAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Group;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.GroupService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

public class ShowGroupsByExercise extends AuthorizedUserAction {
    private static Logger logger = Logger.getLogger(ChangeLanguageAction.class);

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
        String exercise = request.getParameter("exercise");
        logger.info(exercise);
        GroupService service = serviceFactory.getService(GroupService.class);
        List<Group> groups = service.readGroupsByTypeName(exercise);
        if (groups != null) {
            session.setAttribute("exerciseName", exercise);
            session.setAttribute("groupsByExercise", groups);
            logger.info("Groups with certain exercise were shown.");
            return new Forward("/authorized_user/groupsByExercise.jsp", false);
        } else {
            logger.info("Groups were not found!");
            request.setAttribute("message", "Группы не найдены!");
        }
        return null;
    }
}
