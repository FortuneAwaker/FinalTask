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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

public class ShowAllGroups extends AuthorizedUserAction {
    @Override
    public Forward executeAction(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
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
                GroupService service = serviceFactory.getService(GroupService.class);
                List<Group> groups = service.readAll();
                if (groups != null) {
                    ExerciseService exerciseService = serviceFactory.getService(ExerciseService.class);
                    for (Group gr: groups
                         ) {
                        Exercise ex = exerciseService
                                .readById(gr.getTypeOfExercisesId());
                        gr.setTypeOfExercises(ex.getTypeOfExercises());
                    }
                    session.setAttribute("allGroups", groups);
                    return new Forward("/admin/allGroups.jsp", false);
                } else {
                    request.setAttribute("message", "Группы не найдены!");
                }
            } else {
                request.setAttribute("message", "Нужны права администратора!");
            }
        } else {
            request.setAttribute("message", "Нужно войти, чтобы просматривать эту страницу!");
            return new Forward("/index.html");
        }

        return new Forward("/index.html");
    }
}
