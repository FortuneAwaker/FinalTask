package by.popovich.last.action.authorizedUser;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.action.MenuItem;
import by.popovich.last.entity.Person;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.UserInfoService;
import by.popovich.last.service.UserService;
import by.popovich.last.service.UserServiceImpl;
import by.popovich.last.validator.Validator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;
import java.util.ResourceBundle;

public class ChangePasswordAction extends AuthorizedUserAction {
    private static Logger logger = Logger.getLogger(LogoutAction.class);

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
                        6, 25)) {
                    request.setAttribute("message", "Некорректные данные");
                    return null;
                }
                if (password.equals(repeatPassword)) {
                    UserService service = serviceFactory.getService(UserService.class);
                    try {
                        User user = service.readByIdentity(currentUser.getIdentity());
                        user.setPassword(password);
                        service.save(user);
                        session.setAttribute("authorizedUser", user);
                        return new Forward("/index.html");
                    } catch (PersistentException e) {
                        logger.info("Error of changing password");
                        request.setAttribute("message",
                                "Ошибка добавления информации"
                                        + " о пользователе");
                    }
                } else {
                    request.setAttribute("message", "Пароли не совпадают!");
                    logger.info("Passwords are not equal!");
                }
            } else {
                request.setAttribute("message",
                        "Не полностью заполнены необходимые поля");
                logger.info("Fields are not filled!");
            }
        }
        return null;
    }
}
