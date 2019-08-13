package by.popovich.last.action.coach;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
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

public class ShowGroupsOfCoach extends AuthorizedUserAction {
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
        String coachIdFromRequest = request.getParameter("coachId");
        GroupService service = serviceFactory.getService(GroupService.class);
        List<Group> groupList;
        if (coachIdFromRequest != null) {
            Integer coachIdNumber = Integer.parseInt(coachIdFromRequest);
            groupList = service.readGroupsByCoach(coachIdNumber);
            session.setAttribute("coachIdFromRequest", coachIdNumber);
        } else {
            if (currentUser == null) {
                request.setAttribute("message", "Войдите в аккаунт!");
                return new Forward("/index.jsp", false);
            }
            if (currentUser.getRole().equals(Role.COACH)) {
                groupList = service.readGroupsByCoach(currentUser.getIdentity());
            } else {
                request.setAttribute("message", "Недопустимая для действия роль");
                return new Forward("/index.jsp", false);
            }
        }
        ExerciseService exerciseService = serviceFactory.getService(ExerciseService.class);
        for (Group group: groupList
             ) {
            group.setTypeOfExercises(exerciseService.readById(
                    group.getTypeOfExercisesId()).getTypeOfExercises());
        }
        session.setAttribute("listOfGroups", groupList);
        return new Forward("/coach/groups.jsp", false);
    }
}
