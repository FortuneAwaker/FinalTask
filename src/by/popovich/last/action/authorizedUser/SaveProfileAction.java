package by.popovich.last.action.authorizedUser;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.action.LoginAction;
import by.popovich.last.entity.Person;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.UserInfoService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class SaveProfileAction extends AuthorizedUserAction {
    private static Logger logger = Logger.getLogger(LoginAction.class);

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
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String patro = request.getParameter("patro");
            String phoneString = request.getParameter("phone");
            InputStream is = null;
            try {
                Part filePart = request.getPart("avatar");
                if (filePart != null) {
                    logger.info(filePart.getName() + filePart.getSize()
                            + filePart.getContentType());
                    is = filePart.getInputStream();
                }
            } catch (IOException | ServletException e) {
                request.setAttribute("message", "Ошибка обработки картинки");
            }
            Long phone = null;
            if (phoneString != null && !phoneString.isEmpty()) {
                phone = Long.parseLong(phoneString);
            }

            if (name != null && surname != null
                    && patro != null && phone != null) {
                UserInfoService infoService = serviceFactory
                        .getService(UserInfoService.class);
                try {
                    Person person = new Person();
                    person.setIdentity(currentUser.getIdentity());
                    person.setName(name);
                    person.setSurname(surname);
                    person.setPhone(phone);
                    person.setPatronymic(patro);
                    infoService.updateInfo(person, is);
                    session.setAttribute("userInfo", person);
                    request.setAttribute("message", "Изменения сохранены!");
                    return new Forward("/index.html");
                } catch (PersistentException e) {
                    logger.info("Error of adding user info");
                    request.setAttribute("message",
                            "Ошибка добавления информации"
                                    + " о пользователе");
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
