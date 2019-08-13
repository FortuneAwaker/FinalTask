package by.popovich.last.action.admin;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.Subscription;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.SubscriptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

public class ShowAllSubscriptions extends AuthorizedUserAction {
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
                SubscriptionService service = serviceFactory.getService(SubscriptionService.class);
                List<Subscription> subs = service.readAll();
                if (subs != null) {
                    session.setAttribute("allSubs", subs);
                    return new Forward("/admin/allSubscriptions.jsp", false);
                } else {
                    request.setAttribute("message", "Подписки не найдены!");
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
