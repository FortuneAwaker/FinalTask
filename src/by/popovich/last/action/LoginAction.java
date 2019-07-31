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

    static {
        menu.put(Role.COACH, new ArrayList<>(Arrays.asList(
                new MenuItem("/index.html", "Мои группы")
        )));
        menu.put(Role.ADMINISTRATOR, new ArrayList<>(Arrays.asList(

        )));
        menu.put(Role.CLIENT, new ArrayList<>(Arrays.asList(
                new MenuItem("/index.html", "Мои группы")

        )));
    }

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
        String todo = request.getParameter("todo");
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
