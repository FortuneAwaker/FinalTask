package by.popovich.last.action.coach;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Group;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.Subscription;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.ExerciseService;
import by.popovich.last.service.GroupService;
import by.popovich.last.service.SubscriptionService;
import by.popovich.last.service.UserInfoService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

/**
 * Show members of group action.
 */
public class ShowMembersOfGroup extends AuthorizedUserAction {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
            .getLogger(ShowMembersOfGroup.class);

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
        String groupIdFromRequest = request.getParameter("groupId");
        if (currentUser == null) {
            request.setAttribute("message", "Войдите в аккаунт!");
            return new Forward("/index.jsp", false);
        }
        if (groupIdFromRequest != null) {
            Integer groupIdNumber = Integer.parseInt(groupIdFromRequest);
            if (currentUser.getRole().equals(Role.COACH)
                    || currentUser.getRole().equals(Role.ADMINISTRATOR)) {
                GroupService groupService = serviceFactory
                        .getService(GroupService.class);
                Group group = groupService.readById(groupIdNumber);
                if (group.getCoachID().equals(currentUser.getIdentity())) {
                    SubscriptionService service = serviceFactory
                            .getService(SubscriptionService.class);
                    List<Subscription> subs = service
                            .readByGroupId(groupIdNumber);
                    ExerciseService exerciseService = serviceFactory
                            .getService(ExerciseService.class);
                    UserInfoService infoService = serviceFactory
                            .getService(UserInfoService.class);
                    for (Subscription sub : subs
                    ) {
                        sub.setUserInfo(infoService.readById(
                                sub.getClientId()));
                        sub.setTypeOfExercise(
                                exerciseService.readById(
                                        groupService.readById(
                                                sub.getIdOfGroup())
                                                .getTypeOfExercisesId())
                                        .getTypeOfExercises());
                    }
                    session.setAttribute("subscriptionsList", subs);
                    LOGGER.info("Посетители группы были показаны!");
                    return new Forward(
                            "/coach/membersOfGroup.jsp",
                            false);
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
        return new Forward("/index.jsp", false);
    }
}
