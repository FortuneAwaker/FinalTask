package by.popovich.last.action.authorizedUser;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.UserInfoService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Base64;
import java.util.Locale;

/**
 * SHow profile action class.
 */
public class ShowProfileAction extends AuthorizedUserAction {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
            .getLogger(ShowProfileAction.class);

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
        if (currentUser == null) {
            request.setAttribute("message", "Войдите в аккаунт!");
            return new Forward("/index.jsp", false);
        }
        UserInfoService infoService = serviceFactory
                .getService(UserInfoService.class);
        byte[] image = infoService.readImage(currentUser.getIdentity());
        if (image != null) {
            String encode = Base64.getEncoder().encodeToString(image);
            request.setAttribute("image", encode);
        }
        LOGGER.info("Профиль загружен.");
        return new Forward("/authorized_user/profile.jsp",
                false);
    }
}
