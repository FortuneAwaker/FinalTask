package by.popovich.last.action.admin;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Exercise;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.ExerciseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

public class AddExerciseAction extends AuthorizedUserAction {
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
        if (!currentUser.getRole().equals(Role.ADMINISTRATOR)) {
            request.setAttribute("message", "Недопустимая для действия роль");
            return new Forward("/index.jsp", false);
        }
        if (!todo.equals("false")) {
            String nameOfExerciseString = request.getParameter("nameOfExercise");
            if (nameOfExerciseString != null) {
                ExerciseService service = serviceFactory.getService(ExerciseService.class);
                Exercise exerciseToAdd = new Exercise();
                exerciseToAdd.setTypeOfExercises(nameOfExerciseString);
                try {
                    service.save(exerciseToAdd);
                    request.setAttribute("message", "Новое упражнение было добавлено!");
                    return new Forward("/index.jsp", false);
                } catch (PersistentException e) {
                    request.setAttribute("message", "Ошибка создания упражнения!");
                }
            } else {
                request.setAttribute("message", "Недопустимое действие");
            }
        }
        return null;
    }
}
