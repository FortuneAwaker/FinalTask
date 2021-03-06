package by.popovich.last.action;

import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
 * Action - changing locale for resource bundle.
 */
public class ChangeLanguageAction extends Action {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(
            ChangeLanguageAction.class);
    /**
     * Map for storing elements of menu of current user.
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
        LOGGER.info("Язык был установлен как " + lang);
        User currentUser = (User) session.getAttribute("authorizedUser");
        if (currentUser != null) {
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

            session.setAttribute("menu", menu.get(currentUser.getRole()));
        }
        return new Forward("/index.html");
    }
}
