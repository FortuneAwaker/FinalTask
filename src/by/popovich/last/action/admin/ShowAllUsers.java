package by.popovich.last.action.admin;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Person;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.Subscription;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.SubscriptionService;
import by.popovich.last.service.UserInfoService;
import by.popovich.last.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShowAllUsers extends AuthorizedUserAction {
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
                UserService service = serviceFactory.getService(UserService.class);
                UserInfoService infoService = serviceFactory.getService(UserInfoService.class);
                List<Person> allUsers = new ArrayList<>();
                String clientId = request.getParameter("clientId");
                if (clientId != null) {
                    Integer clientIdNumber = Integer.parseInt(clientId);
                    Person person = infoService.readById(clientIdNumber);
                    User user = service.readByIdentity(clientIdNumber);
                    person.setLogin(user.getLogin());
                    person.setRole(user.getRole());
                    allUsers.add(person);
                } else {
                    allUsers = infoService.readAll();
                    for (Person p: allUsers
                         ) {
                        User u = service.readByIdentity(p.getIdentity());
                        p.setRole(u.getRole());
                        p.setLogin(u.getLogin());
                    }
                }
                if (allUsers != null) {
                    session.setAttribute("allUsers", allUsers);
                    return new Forward("/admin/allUsers.jsp", false);
                } else {
                    request.setAttribute("message", "Пользователи не найдены!");
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
