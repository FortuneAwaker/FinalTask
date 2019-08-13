package by.popovich.last.action;


import by.popovich.last.entity.Person;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.UserInfoService;
import by.popovich.last.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LoginAction extends Action {
    private static Logger logger = Logger.getLogger(LoginAction.class);

    private static Map<Role, List<MenuItem>> menu = new ConcurrentHashMap<>();

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
        Locale.setDefault(locale);
        ResourceBundle bundle = ResourceBundle.getBundle("properties.club");
        menu.put(Role.COACH, new ArrayList<>(Arrays.asList(
                new MenuItem("/coach/groups.html",
                        bundle.getString("my_groups_word")),
                new MenuItem("/authorized_user/profile.html",
                        bundle.getString("profile_word"))
        )));
        menu.put(Role.ADMINISTRATOR, new ArrayList<>(Arrays.asList(
                new MenuItem("/admin/allUsers.html",
                        bundle.getString("all_users_word")),
                new MenuItem("/authorized_user/profile.html",
                        bundle.getString("profile_word")),
                new MenuItem("/admin/allSubscriptions.html",
                        bundle.getString("subscription_word")),
                new MenuItem("/admin/allGroups.html",
                        bundle.getString("all_groups_word"))
        )));
        menu.put(Role.CLIENT, new ArrayList<>(Arrays.asList(
                new MenuItem("/authorized_user/profile.html",
                        bundle.getString("profile_word")),
                new MenuItem("/authorized_user/mySubscriptions.html",
                        bundle.getString("my_subscriptions"))

        )));
        String todo = request.getParameter("todo");
        if (todo == null) {
            todo = "true";
        }
        if (!todo.equals("false")) {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            if (login != null && password != null) {
                UserService service = serviceFactory.getService(UserService.class);
                User user = service.readByLoginAndPassword(login, password);
                if (user != null) {
                    if (user.getIdentity() != null) {
                        UserInfoService infoService = serviceFactory.getService(UserInfoService.class);
                        Person userInfo = infoService.readById(user.getIdentity());
                        session.setAttribute("userInfo", userInfo);
                    }
                    session.setAttribute("authorizedUser", user);
                    session.setAttribute("menu", menu.get(user.getRole()));
                    logger.info(String.format("user \"%s\" is logged in from %s (%s:%s)", login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                    return new Forward("/index.html");
                } else {
                    request.setAttribute("message", "Имя пользователя или пароль не опознанны");
                    logger.info(String.format("user \"%s\" unsuccessfully tried to log in from %s (%s:%s)", login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                }
            }
        }
        return null;
    }
}
