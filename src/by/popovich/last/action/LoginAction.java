package by.popovich.last.action;


import by.popovich.last.entity.Person;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.UserInfoService;
import by.popovich.last.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Action to log in.
 */
public class LoginAction extends Action {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
            .getLogger(LoginAction.class);

    /**
     * Elements of menu of current user.
     */
    private static Map<Role, List<MenuItem>> menu = new ConcurrentHashMap<>();

    /**
     * Executes action.
     *
     * @param request  HttpServletRequest.
     * @param response HttpServletResponse
     * @return uri to go.
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
                UserService service = serviceFactory.getService(
                        UserService.class);
                User user = service.readByLoginAndPassword(login, password);
                if (user != null) {
                    if (user.getIdentity() != null) {
                        UserInfoService infoService = serviceFactory
                                .getService(UserInfoService.class);
                        Person userInfo = infoService.readById(
                                user.getIdentity());
                        session.setAttribute("userInfo", userInfo);
                    }
                    session.setAttribute("authorizedUser", user);
                    session.setAttribute("menu", menu.get(user.getRole()));
                    LOGGER.info(String
                            .format("user \"%s\" is logged in from %s (%s:%s)",
                                    login, request.getRemoteAddr(),
                                    request.getRemoteHost(),
                                    request.getRemotePort()));
                    return new Forward("/index.html");
                } else {
                    request.setAttribute("message",
                            "Имя пользователя или пароль не опознанны");
                    LOGGER.info(String.format(
                            "user \"%s\" unsuccessfully tried to"
                                    + " log in from %s (%s:%s)",
                            login, request.getRemoteAddr(),
                            request.getRemoteHost(),
                            request.getRemotePort()));
                }
            }
        }
        return null;
    }
}
