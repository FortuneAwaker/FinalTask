package by.popovich.last.action.authorizedUser;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.ChangeLanguageAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.Subscription;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.ExerciseService;
import by.popovich.last.service.GroupService;
import by.popovich.last.service.SubscriptionService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

public class ShowSubscribtionsAction extends AuthorizedUserAction {
    private static Logger logger = Logger.getLogger(ShowSubscribtionsAction.class);

    @Override
    public Forward executeAction(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        HttpSession session = request.getSession();
        Locale locale;
        String lang = request.getParameter("lang");
        if (lang == null) {
            lang = "ru";
        }
        locale = new Locale(lang);
        request.setAttribute("lang", lang);
        session.setAttribute("lang", lang);
        Config.set(request, Config.FMT_LOCALE, locale);
        User currentUser = (User) session.getAttribute("authorizedUser");
        if (currentUser == null) {
            request.setAttribute("message", "Войдите в аккаунт!");
            return new Forward("/index.jsp", false);
        }
        if (currentUser.getRole().equals(Role.CLIENT)) {
            int clientId = currentUser.getIdentity();
            SubscriptionService service = serviceFactory.getService(SubscriptionService.class);
            GroupService groupService = serviceFactory.getService(GroupService.class);
            ExerciseService exerciseService = serviceFactory.getService(ExerciseService.class);
            List<Subscription> subscriptions = service.readSubscriptionsByClientId(clientId);
            for (Subscription subscription: subscriptions
                 ) {
                subscription.setTypeOfExercise(
                        exerciseService.readById(
                                groupService.readById(
                                        subscription.getIdOfGroup())
                        .getTypeOfExercisesId())
                                .getTypeOfExercises()
                );
                subscription.setCoachId(groupService.readById(
                        subscription.getIdOfGroup()).getCoachID());
            }
            session.setAttribute("listOfSubscriptions", subscriptions);
            logger.info("Подписки были показаны пользователю");
            return new Forward(
                    "/authorized_user/mySubscriptions.jsp",
                    false);
        } else {
            request.setAttribute("message", "Недопустимая для действия роль");
            return new Forward("/index.jsp", false);
        }
    }
}
