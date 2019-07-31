package by.popovich.last.action.menu;

import by.popovich.last.action.Action;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Exercise;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.ExerciseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

public class ShowExercisesAction extends Action {
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
        ExerciseService service = serviceFactory.getService(ExerciseService.class);
        List<Exercise> exercises = service.readAll();
        if (exercises != null) {
            if (request.getAttribute("listOfExercises") == null) {
                request.setAttribute("listOfExercises", exercises);
                LOGGER.info("List of exercises was shown successfully");
            }
        }
        return new Forward("/menu/exercises.jsp", false);
    }
}
