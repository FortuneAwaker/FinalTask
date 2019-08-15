package by.popovich.last.action.coach;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Group;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.GroupService;
import by.popovich.last.validator.Validator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

/**
 * Edit group action class.
 */
public class EditGroupAction extends AuthorizedUserAction {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
            .getLogger(EditGroupAction.class);

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
        String groupIdFromRequest = request.getParameter("groupId");
        if (groupIdFromRequest != null) {
            session.setAttribute("idOfGroupToEdit", groupIdFromRequest);
        }
        if (!todo.equals("false")) {
            if (groupIdFromRequest != null) {
                Integer groupIdNumber = Integer.parseInt(groupIdFromRequest);
                if (currentUser.getRole().equals(Role.COACH)) {
                    String numberOfClientsString = request
                            .getParameter("numberOfClients");
                    if (numberOfClientsString != null) {
                        Validator validator = new Validator();
                        if (!(validator.validateNumber(
                                numberOfClientsString, 1, three))) {
                            LOGGER.info("Некорректные данные!");
                            request.setAttribute("message",
                                    "Некорректные данные!");
                            return null;
                        }
                        Integer numberOfClients = Integer
                                .parseInt(numberOfClientsString);
                        GroupService service = serviceFactory
                                .getService(GroupService.class);
                        Group groupToEdit = service.readById(groupIdNumber);
                        if (numberOfClients <= groupToEdit
                                .getCurrentClients()) {
                            request.setAttribute("message",
                                    "Невозможно уменьшить количество "
                                            + "клиентов таким образом!");
                        } else {
                            groupToEdit.setMaxClients(numberOfClients);
                            service.save(groupToEdit);
                            LOGGER.info("Группа была изменена!");
                            request.setAttribute("message",
                                    "Группа была изменена!");
                            return new Forward("/index.jsp",
                                    false);
                        }
                    } else {
                        request.setAttribute("message",
                                "Недопустимое действие!");
                        return new Forward("/index.jsp",
                                false);
                    }
                } else {
                    request.setAttribute("message",
                            "Недопустимая для действия роль!");
                    return new Forward("/index.jsp",
                            false);
                }
            }
        }
        return null;
    }
}
