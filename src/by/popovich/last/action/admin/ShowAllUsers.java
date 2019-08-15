package by.popovich.last.action.admin;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Person;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.UserInfoService;
import by.popovich.last.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Show all users action class.
 */
public class ShowAllUsers extends AuthorizedUserAction {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
            .getLogger(ShowAllUsers.class);

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
                UserService service = serviceFactory
                        .getService(UserService.class);
                UserInfoService infoService = serviceFactory
                        .getService(UserInfoService.class);
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
                    for (Person p : allUsers
                    ) {
                        User u = service.readByIdentity(p.getIdentity());
                        p.setRole(u.getRole());
                        p.setLogin(u.getLogin());
                    }
                }
                if (allUsers != null) {
                    session.setAttribute("allUsers", allUsers);
                    return new Forward("/admin/allUsers.jsp",
                            false);
                } else {
                    LOGGER.info("Пользователи не найдены!");
                    request.setAttribute("message",
                            "Пользователи не найдены!");
                }
            } else {
                LOGGER.info("Нужны права администратора!");
                request.setAttribute("message",
                        "Нужны права администратора!");
            }
        } else {
            LOGGER.info("Нужно войти, чтобы просматривать эту страницу!");
            request.setAttribute("message",
                    "Нужно войти, чтобы просматривать эту страницу!");
            return new Forward("/index.html");
        }

        return new Forward("/index.html");
    }
}
