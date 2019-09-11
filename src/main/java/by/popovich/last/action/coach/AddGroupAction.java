package by.popovich.last.action.coach;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Exercise;
import by.popovich.last.entity.Group;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.ExerciseService;
import by.popovich.last.service.GroupService;
import by.popovich.last.validator.Validator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

/**
 * Add group action class.
 */
public class AddGroupAction extends AuthorizedUserAction {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
            .getLogger(AddGroupAction.class);
    /**
     * Three.
     */
    private final int three = 3;
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
        if (currentUser.getRole().equals(Role.COACH)) {
            ExerciseService service = serviceFactory
                    .getService(ExerciseService.class);
            List<Exercise> exercises = service.readAll();
            session.setAttribute("exercisesToChoose", exercises);
        } else {
            LOGGER.info("Недопустимая для действия роль!");
            request.setAttribute("message",
                    "Недопустимая для действия роль!");
            return new Forward("/index.jsp", false);
        }
        if (!todo.equals("false")) {
            String exerciseId = request.getParameter("chosenExercise");
            String numberOfClientsString = request
                    .getParameter("numberOfClients");
            if (numberOfClientsString != null
                    && exerciseId != null) {
                Validator validator = new Validator();
                if (!(validator.validateNumber(
                        numberOfClientsString, 1, three))) {
                    LOGGER.info("Некорректные данные!");
                    request.setAttribute("message",
                            "Некорректные данные!");
                    return null;
                }
                ExerciseService exerciseService = serviceFactory
                        .getService(ExerciseService.class);
                GroupService service = serviceFactory
                        .getService(GroupService.class);
                Exercise exercise = exerciseService
                        .readById(Integer.parseInt(exerciseId));
                Group groupToAdd = new Group();
                if (exercise != null) {
                    groupToAdd.setTypeOfExercisesId(exercise.getIdentity());
                    groupToAdd.setTypeOfExercises(
                            exercise.getTypeOfExercises());
                } else {
                    LOGGER.info("Ошибка получения упражнения!");
                    request.setAttribute("message",
                            "Ошибка получения упражнения!");
                    return null;
                }
                groupToAdd.setCurrentClients(0);
                Integer numberOfClients = Integer
                        .parseInt(numberOfClientsString);
                if (numberOfClients <= 0) {
                    LOGGER.info("Неверное значение числа клиентов!");
                    request.setAttribute("message",
                            "Неверное значение числа клиентов!");
                }
                groupToAdd.setMaxClients(numberOfClients);
                groupToAdd.setCoachID(currentUser.getIdentity());
                try {
                    service.save(groupToAdd);
                    LOGGER.info("Новая группа была создана!");
                    request.setAttribute("message",
                            "Новая группа была создана!");
                    return new Forward("/index.jsp",
                            false);
                } catch (PersistentException e) {
                    LOGGER.info("Ошибка создания группы!");
                    request.setAttribute("message",
                            "Ошибка создания группы!");
                }
            }
        }
        return null;
    }
}
