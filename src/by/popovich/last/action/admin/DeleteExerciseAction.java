package by.popovich.last.action.admin;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.ExerciseService;
import by.popovich.last.service.PriceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

public class DeleteExerciseAction extends AuthorizedUserAction {
    @Override
    public Forward executeAction(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        HttpSession session = request.getSession();
        Locale locale;
        String lang = (String) session.getAttribute("lang");
        if (lang == null) {
            lang = "ru";
        }
        locale = new Locale(lang);
        request.setAttribute("lang", lang);
        session.setAttribute("lang", lang);
        Config.set(request, Config.FMT_LOCALE, locale);
        User currentUser = (User) session.getAttribute("authorizedUser");
        String exerciseIdFromRequest = request.getParameter("exerciseId");
        if (currentUser == null) {
            request.setAttribute("message", "Войдите в аккаунт!");
            return new Forward("/index.jsp", false);
        }
        if (exerciseIdFromRequest != null) {
            Integer exerciseIdNumber = Integer.parseInt(exerciseIdFromRequest);
            if (currentUser.getRole().equals(Role.ADMINISTRATOR)) {
                ExerciseService service = serviceFactory.getService(ExerciseService.class);
                service.delete(exerciseIdNumber);
                request.setAttribute("message", "Упражнение удалено!");
            } else {
                request.setAttribute("message", "Недопустимая для действия роль");
                return new Forward("/index.jsp", false);
            }
        } else {
            request.setAttribute("message", "Недопустимое действие");
        }
        return new Forward("/index.jsp", false);
    }
}
