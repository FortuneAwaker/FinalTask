package by.popovich.last.action.coach;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Group;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.Subscription;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.GroupService;
import by.popovich.last.service.SubscriptionService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

/**
 * Notify visit action class.
 */
public class NotifyVisitAction extends AuthorizedUserAction {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
            .getLogger(NotifyVisitAction.class);

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
        String subIdFromRequest = request.getParameter("subId");
        Integer groupId;
        if (currentUser == null) {
            request.setAttribute("message", "Войдите в аккаунт!");
            return new Forward("/index.jsp", false);
        }
        if (subIdFromRequest != null) {
            Integer subIdNumber = Integer.parseInt(subIdFromRequest);
            SubscriptionService service = serviceFactory
                    .getService(SubscriptionService.class);
            Subscription sub = service.readById(subIdNumber);
            groupId = sub.getIdOfGroup();
            if (currentUser.getRole().equals(Role.COACH)
                    || currentUser.getRole().equals(Role.ADMINISTRATOR)) {
                GroupService groupService = serviceFactory
                        .getService(GroupService.class);
                Group group = groupService.readById(groupId);
                if (group.getCoachID().equals(currentUser.getIdentity())) {
                    if (sub.getLeftVisits() > 0) {
                        sub.setLeftVisits(sub.getLeftVisits() - 1);
                        service.save(sub);
                        LOGGER.info("Посещение отмечено.");
                        request.setAttribute("message",
                                "Посещение отмечено.");
                    } else {
                        LOGGER.info("Посещений не осталось!");
                        request.setAttribute("message",
                                "Посещений не осталось!");
                    }
                }
            } else {
                request.setAttribute("message",
                        "Недопустимое действие");
                return new Forward("/index.jsp",
                        false);
            }
        } else {
            request.setAttribute("message",
                    "Недопустимая для действия роль");
            return new Forward("/index.jsp",
                    false);
        }
        return new Forward("/coach/membersOfGroup.html?groupId=" + groupId);

    }
}
