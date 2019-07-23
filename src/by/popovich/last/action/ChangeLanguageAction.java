package by.popovich.last.action;

import by.popovich.last.controller.DispatcherServlet;
import by.popovich.last.exception.PersistentException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

public class ChangeLanguageAction extends Action {
    private static Logger logger = Logger.getLogger(ChangeLanguageAction.class);

    @Override
    public Forward executeAction(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Locale locale;
        String lang = request.getParameter("lang");
        if (lang == null) {
            lang = "ru";
        }
        locale = new Locale(lang);
        request.setAttribute("lang", lang);
        Config.set(request, Config.FMT_LOCALE, locale);
        return new Forward("/index.html");
    }
}
