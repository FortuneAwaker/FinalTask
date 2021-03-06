package by.popovich.last.action.admin;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Group;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.Subscription;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.GroupService;
import by.popovich.last.service.SubscriptionService;
import by.popovich.last.service.UserInfoService;
import by.popovich.last.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

/**
 * Delete user action class.
 */
public class DeleteUserAction extends AuthorizedUserAction {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteUserAction.class);

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
                UserService service = serviceFactory
                        .getService(UserService.class);
                UserInfoService info = serviceFactory
                        .getService(UserInfoService.class);
                SubscriptionService subService = serviceFactory
                        .getService(SubscriptionService.class);
                GroupService gs = serviceFactory
                        .getService(GroupService.class);
                info.delete(userId);
                List<Group> groups = gs.readGroupsByCoach(userId);
                for (Group g : groups
                ) {
                    if (subService.readByGroupId(g.getIdentity()) == null) {
                        gs.delete(g.getIdentity());
                    }
                }
                List<Subscription> subs = subService
                        .readSubscriptionsByClientId(userId);
                for (Subscription s : subs
                ) {
                    subService.delete(s.getIdentity());
                }
                service.delete(userId);
                LOGGER.info("Пользователь удалён!");
                request.setAttribute("message", "Пользователь удалён!");
            } else {
                LOGGER.info("Недопустимая для действия роль!");
                request.setAttribute("message",
                        "Недопустимая для действия роль!");
                return new Forward("/index.jsp",
                        false);
            }
        } else {
            LOGGER.info("Недопустимое действие!");
            request.setAttribute("message", "Недопустимое действие!");
        }
        return new Forward("/admin/allUsers.html");
    }
}
