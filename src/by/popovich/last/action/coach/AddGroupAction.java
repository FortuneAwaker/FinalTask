package by.popovich.last.action.coach;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.action.LoginAction;
import by.popovich.last.entity.Exercise;
import by.popovich.last.entity.Group;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.ExerciseService;
import by.popovich.last.service.GroupService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

public class AddGroupAction extends AuthorizedUserAction {
    private static Logger logger = Logger.getLogger(AddGroupAction.class);


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
            ExerciseService service = serviceFactory.getService(ExerciseService.class);
            List<Exercise> exercises = service.readAll();
            session.setAttribute("exercisesToChoose", exercises);
        } else {
            request.setAttribute("message", "Недопустимая для действия роль");
            return new Forward("/index.jsp", false);
        }
        if (!todo.equals("false")) {
            String exerciseId = request.getParameter("chosenExercise");
            logger.info(exerciseId);
            String numberOfClientsString = request.getParameter("numberOfClients");
            if (numberOfClientsString != null
                    && exerciseId != null) {
                ExerciseService exerciseService = serviceFactory.getService(ExerciseService.class);
                GroupService service = serviceFactory.getService(GroupService.class);
                Exercise exercise = exerciseService.readById(Integer.parseInt(exerciseId));
                Group groupToAdd = new Group();
                if (exercise != null) {
                    groupToAdd.setTypeOfExercisesId(exercise.getIdentity());
                    groupToAdd.setTypeOfExercises(exercise.getTypeOfExercises());
                } else {
                    request.setAttribute("message", "Ошибка получения упражнения!");
                    return null;
                }
                groupToAdd.setCurrentClients(0);
                groupToAdd.setMaxClients(Integer.parseInt(numberOfClientsString));
                groupToAdd.setCoachID(currentUser.getIdentity());
                try {
                    service.save(groupToAdd);
                    request.setAttribute("message", "Новая группа была создана!");
                    return new Forward("/index.jsp", false);
                } catch (PersistentException e) {
                    request.setAttribute("message", "Ошибка создания группы!");
                }
            }
        }
        return null;
    }
}
