package by.popovich.last.action.admin;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Exercise;
import by.popovich.last.entity.Group;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.ExerciseService;
import by.popovich.last.service.GroupService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

/**
 * Show all groups action.
 */
public class ShowAllGroups extends AuthorizedUserAction {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
            .getLogger(ShowAllGroups.class);

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
        User currentUser = (User) session.getAttribute("authorizedUser");
        if (currentUser != null) {
            if (currentUser.getRole().equals(Role.ADMINISTRATOR)) {
                String lang = (String) session.getAttribute("lang");
                if (lang == null) {
                    lang = "ru";
                    session.setAttribute("lang", lang);
                }
                request.setAttribute("lang", lang);
                Locale locale = new Locale(lang);
                Config.set(request, Config.FMT_LOCALE, locale);
                GroupService service = serviceFactory
                        .getService(GroupService.class);
                List<Group> groups = service.readAll();
                if (groups != null) {
                    ExerciseService exerciseService = serviceFactory
                            .getService(ExerciseService.class);
                    for (Group gr : groups
                    ) {
                        Exercise ex = exerciseService
                                .readById(gr.getTypeOfExercisesId());
                        gr.setTypeOfExercises(ex.getTypeOfExercises());
                    }
                    session.setAttribute("allGroups", groups);
                    return new Forward("/admin/allGroups.jsp",
                            false);
                } else {
                    LOGGER.info("Группы не найдены!");
                    request.setAttribute("message",
                            "Группы не найдены!");
                }
            } else {
                LOGGER.info("Нужны права администратора!");
                request.setAttribute("message",
                        "Нужны права администратора!");
            }
        } else {
            LOGGER.info("Нужно войти, чтобы просматривать эту страницу!");
            request.setAttribute("message",
                    "Нужно войти, чтобы просматривать эту страницу!");
            return new Forward("/index.html");
        }

        return new Forward("/index.html");
    }
}
