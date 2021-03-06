package by.popovich.last.action.admin;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Exercise;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.ExerciseService;
import by.popovich.last.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

/**
 * Edit exercise action class.
 */
public class EditExerciseAction extends AuthorizedUserAction {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditExerciseAction.class);
    /**
     * Three.
     */
    private final int three = 3;
    /**
     * Fifteen.
     */
    private final int fifteen = 15;
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
        HttpSession session = request.getSession(true);
        String lang = (String) session.getAttribute("lang");
        if (lang == null) {
            lang = "ru";
            session.setAttribute("lang", lang);
        }
        request.setAttribute("lang", lang);
        Locale locale = new Locale(lang);
        Config.set(request, Config.FMT_LOCALE, locale);
        User currentUser = (User) session.getAttribute("authorizedUser");
        if (currentUser == null) {
            request.setAttribute("message", "Войдите в аккаунт!");
            return new Forward("/index.jsp", false);
        }
        String todo = request.getParameter("todo");
        if (todo == null) {
            todo = "true";
        }
        String exerciseIdFromRequest = request.getParameter("exerciseId");
        if (exerciseIdFromRequest != null) {
            session.setAttribute("idOfExerciseToEdit",
                    exerciseIdFromRequest);
        }
        if (!todo.equals("false")) {
            if (exerciseIdFromRequest != null) {
                Integer exerciseIdNumber = Integer
                        .parseInt(exerciseIdFromRequest);
                if (currentUser.getRole().equals(Role.ADMINISTRATOR)) {
                    String nameOfExerciseString = request
                            .getParameter("nameOfExercise");
                    if (nameOfExerciseString != null) {
                        Validator validator = new Validator();
                        if (!(validator.validateStringData(nameOfExerciseString,
                                three, fifteen))) {
                            LOGGER.info("Некорректные данные!");
                            request.setAttribute("message",
                                    "Некорректные данные!");
                            return null;
                        }
                        ExerciseService service = serviceFactory
                                .getService(ExerciseService.class);
                        Exercise exerciseToEdit = service
                                .readById(exerciseIdNumber);
                        exerciseToEdit.setTypeOfExercises(nameOfExerciseString);
                        service.save(exerciseToEdit);
                        LOGGER.info("Упражнение было изменено!");
                        request.setAttribute("message",
                                "Упражнение было изменено!");
                        return new Forward("/index.jsp",
                                false);
                    } else {
                        LOGGER.info("Недопустимое действие!");
                        request.setAttribute("message",
                                "Недопустимое действие!");
                        return new Forward("/index.jsp",
                                false);
                    }
                } else {
                    LOGGER.info("Недопустимая для действия роль!");
                    request.setAttribute("message",
                            "Недопустимая для действия роль!");
                    return new Forward("/index.jsp",
                            false);
                }
            } else {
                LOGGER.info("Недопустимое действие!");
                request.setAttribute("message",
                        "Недопустимое действие!");
            }
        }
        return null;
    }
}
