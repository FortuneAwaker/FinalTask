package by.popovich.last.action;

import by.popovich.last.controller.DispatcherServlet;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChangeLanguageAction extends Action {
    private static Logger logger = Logger.getLogger(ChangeLanguageAction.class);

    private static Map<Role, List<MenuItem>> menu = new ConcurrentHashMap<>();

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
        User currentUser = (User)session.getAttribute("authorizedUser");
        if (currentUser != null) {
            Locale.setDefault(locale);
            ResourceBundle bundle = ResourceBundle.getBundle("properties.club");
            menu.put(Role.COACH, new ArrayList<>(Arrays.asList(
                    new MenuItem("/coach/groups.html",
                            bundle.getString("my_groups_word")),
                    new MenuItem("/authorizedUser/profile.html",
                            bundle.getString("profile_word"))
            )));
            menu.put(Role.ADMINISTRATOR, new ArrayList<>(Arrays.asList(
                    new MenuItem("/admin/allUsers.html",
                            bundle.getString("all_users_word")),
                    new MenuItem("/authorizedUser/profile.html",
                            bundle.getString("profile_word"))
            )));
            menu.put(Role.CLIENT, new ArrayList<>(Arrays.asList(
                    new MenuItem("/client/groups.html",
                            bundle.getString("my_groups_word")),
                    new MenuItem("/authorizedUser/profile.html",
                            bundle.getString("profile_word")),
                    new MenuItem("/mySubscriptions.html",
                            bundle.getString("my_subscriptions"))

            )));

            session.setAttribute("menu" , menu.get(currentUser.getRole()));
        }
        return new Forward("/index.html");
    }
}
