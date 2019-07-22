package by.popovich.last.action;

import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction extends AuthorizedUserAction {
    private static Logger logger = Logger.getLogger(LogoutAction.class);

    @Override
    public Forward executeAction(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        User user = getAuthorizedUser();
        user = (User) request.getSession(false).getAttribute("authorizedUser");
        logger.info(String.format("user \"%s\" is logged out", user.getLogin()));
        request.getSession(false).invalidate();
        return new Forward("/index.html");
    }
}
