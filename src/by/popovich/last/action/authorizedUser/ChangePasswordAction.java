package by.popovich.last.action.authorizedUser;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.UserService;
import by.popovich.last.validator.Validator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

/**
 * Change password action class.
 */
public class ChangePasswordAction extends AuthorizedUserAction {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
            .getLogger(ChangePasswordAction.class);
    /**
     * Six.
     */
    private final int six = 6;
    /**
     * Fifteen.
     */
    private final int fifteen = 15;

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
        HttpSession session = request.getSession(true);
        String lang = (String) session.getAttribute("lang");
        if (lang == null) {
            lang = "ru";
            session.setAttribute("lang", lang);
        }
        request.setAttribute("lang", lang);
        Locale locale = new Locale(lang);
        Config.set(request, Config.FMT_LOCALE, locale);
        User currentUser = (User) session.getAttribute("authorizedUser");
        if (currentUser == null) {
            request.setAttribute("message", "Войдите в аккаунт!");
            return new Forward("/index.jsp", false);
        }
        String todo = request.getParameter("todo");
        if (todo == null) {
            todo = "true";
        }
        if (!todo.equals("false")) {
            String password = request.getParameter("password");
            String repeatPassword = request.getParameter("repeat");
            if (password != null && repeatPassword != null) {
                Validator validator = new Validator();
                if (!validator.validateLoginOrPassword(password,
                        six, fifteen)) {
                    LOGGER.info("Некорректные данные!");
                    request.setAttribute("message",
                            "Некорректные данные!");
                    return null;
                }
                if (password.equals(repeatPassword)) {
                    UserService service = serviceFactory
                            .getService(UserService.class);
                    try {
                        User user = service.readByIdentity(
                                currentUser.getIdentity());
                        user.setPassword(password);
                        service.save(user);
                        session.setAttribute("authorizedUser", user);
                        return new Forward("/index.html");
                    } catch (PersistentException e) {
                        LOGGER.info("Ошибка смены пароля!");
                        request.setAttribute("message",
                                "Ошибка смены пароля!");
                    }
                } else {
                    request.setAttribute("message",
                            "Пароли не совпадают!");
                    LOGGER.info("Пароли не совпадают!");
                }
            } else {
                request.setAttribute("message",
                        "Не полностью заполнены необходимые поля!");
                LOGGER.info("Не полностью заполнены необходимые поля!");
            }
        }
        return null;
    }
}
