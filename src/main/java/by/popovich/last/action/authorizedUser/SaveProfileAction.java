package by.popovich.last.action.authorizedUser;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Person;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.UserInfoService;
import by.popovich.last.validator.Validator;
import org.apache.log4j.LogManager;
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

/**
 * Save profile action class.
 */
public class SaveProfileAction extends AuthorizedUserAction {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
            .getLogger(SaveProfileAction.class);
    /**
     * Four.
     */
    private final int four = 4;
    /**
     * Fifteen.
     */
    private final int fifteen = 15;
    /**
     * Seven.
     */
    private final int seven = 7;
    /**
     * Twelve.
     */
    private final int twelve = 12;

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
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String patro = request.getParameter("patro");
            String phoneString = request.getParameter("phone");
            InputStream is = null;
            Validator validator = new Validator();
            if (!(validator.validateStringData(name,
                    four, fifteen)
                    && validator.validateStringData(
                    surname, four, fifteen)
                    && validator.validateStringData(
                    patro, four, fifteen)
                    && validator.validateNumber(
                    phoneString, seven, twelve))) {
                LOGGER.info("Некорректные данные!");
                request.setAttribute("message", "Некорректные данные!");
                return null;
            }
            try {
                Part filePart = request.getPart("avatar");
                if (filePart != null) {
                    LOGGER.info(filePart.getName() + filePart.getSize()
                            + filePart.getContentType());
                    is = filePart.getInputStream();
                }
            } catch (IOException | ServletException e) {
                LOGGER.info("Ошибка обработки картинки!");
                request.setAttribute(
                        "message", "Ошибка обработки картинки!");
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
                    LOGGER.info("Изменения сохранены!");
                    request.setAttribute(
                            "message", "Изменения сохранены!");
                    return new Forward("/index.html");
                } catch (PersistentException e) {
                    LOGGER.info("Ошибка добавления информации о пользователе! "
                            + "Возможно, выбран неверный "
                            + "формат картинки.");
                    request.setAttribute("message",
                            "Ошибка добавления информации о пользователе! "
                                    + "Возможно, выбран неверный "
                                    + "формат картинки.");
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
