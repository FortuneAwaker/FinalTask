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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

public class EditGroupAction extends AuthorizedUserAction {
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
        String groupIdFromRequest = request.getParameter("groupId");
        if (groupIdFromRequest != null) {
            session.setAttribute("idOfGroupToEdit", groupIdFromRequest);
        }
        if (!todo.equals("false")) {
            if (groupIdFromRequest != null) {
                Integer groupIdNumber = Integer.parseInt(groupIdFromRequest);
                if (currentUser.getRole().equals(Role.COACH)) {
                    String numberOfClientsString = request.getParameter("numberOfClients");
                    if (numberOfClientsString != null) {
                        Integer numberOfClients = Integer.parseInt(numberOfClientsString);
                        GroupService service = serviceFactory.getService(GroupService.class);
                        Group groupToEdit = service.readById(groupIdNumber);
                        if (numberOfClients <= groupToEdit.getCurrentClients()) {
                            request.setAttribute("message",
                                    "Невозможно уменьшить количество "
                                            + "клиентов таким образом!");
                        } else {
                            groupToEdit.setMaxClients(numberOfClients);
                            service.save(groupToEdit);
                            request.setAttribute("message", "Группа была изменена!");
                            return new Forward("/index.jsp", false);
                        }
                    } else {
                        request.setAttribute("message", "Недопустимое действие");
                        return new Forward("/index.jsp", false);
                    }
                } else {
                    request.setAttribute("message", "Недопустимая для действия роль");
                    return new Forward("/index.jsp", false);
                }
            }
        }
        return null;
    }
}
