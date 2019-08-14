package by.popovich.last.action.admin;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

public class ChangeRoleAction extends AuthorizedUserAction {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(ChangeRoleAction.class);

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
        String userIdString = request.getParameter("userId");
        if (currentUser == null) {
            request.setAttribute("message", "Войдите в аккаунт!");
            return new Forward("/index.jsp", false);
        }
        if (userIdString != null) {
            Integer userId = Integer.parseInt(userIdString);
            if (currentUser.getRole().equals(Role.ADMINISTRATOR)) {
                UserService service = serviceFactory.getService(UserService.class);
                User user = service.readByIdentity(userId);
                if (user.getRole().equals(Role.CLIENT)) {
                    user.setRole(Role.COACH);
                } else if (user.getRole().equals(Role.COACH)) {
                    user.setRole(Role.CLIENT);
                } else {
                    LOGGER.info("Невозможное действие!");
                    request.setAttribute("message", "Это невозможно!");
                    return new Forward("/admin/allUsers.jsp");
                }
                service.save(user);
                LOGGER.info("Роль изменена!");
                request.setAttribute("message", "Роль изменена!");
            } else {
                LOGGER.info("Недопустимая для действия роль");
                request.setAttribute("message", "Недопустимая для действия роль");
                return new Forward("/index.jsp", false);
            }
        } else {
            LOGGER.info("Недопустимое действие");
            request.setAttribute("message", "Недопустимое действие");
        }
        return new Forward("/admin/allUsers.html");
    }
}
