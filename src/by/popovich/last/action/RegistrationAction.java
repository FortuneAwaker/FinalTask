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
import java.util.ArrayList;

public class RegistrationAction extends Action {
    private static Logger logger = Logger.getLogger(LoginAction.class);

    private static ArrayList<MenuItem> menu = new ArrayList<>();

    static {
        menu.add(new MenuItem("/editProfile.html",
                        "Профиль"));
        menu.add(new MenuItem("/mySubscriptions.html",
                "Подписки"));
    }

    @Override
    public Forward executeAction(HttpServletRequest request,
                                 HttpServletResponse response) {
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeat");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String patro = request.getParameter("patro");
        Long phone = Long.parseLong(request.getParameter("phone"));
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
        return null;
    }
}
