package by.popovich.last.action.admin;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.PriceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

public class DeletePriceAction extends AuthorizedUserAction {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeletePriceAction.class);

    @Override
    public Forward executeAction(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        HttpSession session = request.getSession();
        Locale locale;
        String lang = (String) session.getAttribute("lang");
        if (lang == null) {
            lang = "ru";
        }
        locale = new Locale(lang);
        request.setAttribute("lang", lang);
        session.setAttribute("lang", lang);
        Config.set(request, Config.FMT_LOCALE, locale);
        User currentUser = (User) session.getAttribute("authorizedUser");
        String priceIdFromRequest = request.getParameter("priceId");
        if (currentUser == null) {
            request.setAttribute("message", "Войдите в аккаунт!");
            return new Forward("/index.jsp", false);
        }
        if (priceIdFromRequest != null) {
            Integer priceIdNumber = Integer.parseInt(priceIdFromRequest);
            if (currentUser.getRole().equals(Role.ADMINISTRATOR)) {
                PriceService service = serviceFactory.getService(PriceService.class);
                service.delete(priceIdNumber);
                LOGGER.info("Расценка удалена!");
                request.setAttribute("message", "Расценка удалена!");
            } else {
                LOGGER.info("Недопустимая для действия роль!");
                request.setAttribute("message", "Недопустимая для действия роль!");
                return new Forward("/index.jsp", false);
            }
        } else {
            LOGGER.info("Недопустимое действие!");
            request.setAttribute("message", "Недопустимое действие!");
        }
        return new Forward("/index.jsp", false);
    }
}
