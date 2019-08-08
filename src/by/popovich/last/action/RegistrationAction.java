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
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegistrationAction extends Action {
    private static Logger logger = Logger.getLogger(LoginAction.class);

    private static ArrayList<MenuItem> menu = new ArrayList<>();

    @Override
    public Forward executeAction(HttpServletRequest request,
                                 HttpServletResponse response) {
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
        if (todo == null) {
            todo = "true";
        }
        if (!todo.equals("false")) {
            if (menu.isEmpty()) {
                Locale.setDefault(locale);
                ResourceBundle bundle = ResourceBundle.getBundle("properties.club");
//                menu.add(new MenuItem("/authorized_user/groups.html",
//                        bundle.getString("my_groups_word")));
                menu.add(new MenuItem("/authorized_user/profile.html",
                        bundle.getString("profile_word")));
                menu.add(new MenuItem("/authorized_user/mySubscriptions.html",
                        bundle.getString("my_subscriptions")));
            }
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String repeatPassword = request.getParameter("repeat");
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String patro = request.getParameter("patro");
            String phoneString = request.getParameter("phone");
            Long phone = null;
            if (phoneString != null && !phoneString.isEmpty()) {
                phone = Long.parseLong(phoneString);
            }
            //аватарка
            User newUser = new User();
            Person person = new Person();
            if (login != null && password != null && repeatPassword != null
                    && name != null && surname != null
                    && patro != null && phone != null) {
                if (password.equals(repeatPassword)) {
                    newUser.setRole(Role.CLIENT);
                    newUser.setLogin(login);
                    newUser.setPassword(password);
                    try {
                        UserService userService = serviceFactory
                                .getService(UserService.class);
                        userService.save(newUser);
                        person.setName(name);
                        person.setSurname(surname);
                        person.setPatronymic(patro);
                        person.setPhone(phone);
                        person.setIdentity(newUser.getIdentity());
                        UserInfoService infoService = serviceFactory
                                .getService(UserInfoService.class);
                        try {
                            infoService.addInfo(person);
                            session.setAttribute("userInfo", person);
                            session.setAttribute("authorizedUser", newUser);
                            session.removeAttribute("menu");
                            session.setAttribute("menu", menu);
                            return new Forward("/index.html");
                        } catch (PersistentException e) {
                            logger.info("Error of adding user info");
                            request.setAttribute("message",
                                    "Ошибка добавления информации"
                                            + " о пользователе");
                        }
                    } catch (PersistentException exception) {
                        request.setAttribute("message",
                                "Пользователь с таким логином уже существует!");
                        logger.info("User with such login exists!");
                    }
                } else {
                    request.setAttribute("message", "Пароли не совпадают!");
                    logger.info("Passwords are not equal!");
                    logger.info(String.format("user \"%s\" unsuccessfully tried "
                                    + "to sign up from %s (%s:%s)", login,
                            request.getRemoteAddr(), request.getRemoteHost(),
                            request.getRemotePort()));
                }
            } else {
                request.setAttribute("message",
                        "Не полностью заполнены необходимые поля");
                logger.info("Fields are not filled!");
                logger.info(String.format("user \"%s\" unsuccessfully "
                                + "tried to sign up from %s (%s:%s)", login,
                        request.getRemoteAddr(), request.getRemoteHost(),
                        request.getRemotePort()));
            }
        }
        return null;
    }
}
