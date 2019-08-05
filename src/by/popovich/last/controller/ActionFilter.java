package by.popovich.last.controller;

import by.popovich.last.action.*;
import by.popovich.last.action.authorizedUser.LogoutAction;
import by.popovich.last.action.menu.ShowCoachesAction;
import by.popovich.last.action.menu.ShowExercisesAction;
import by.popovich.last.action.authorizedUser.ShowGroupsByExercise;
import by.popovich.last.action.menu.ShowTicketsAction;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionFilter implements Filter {
    private static Logger logger = Logger.getLogger(ActionFilter.class);

    private static Map<String, Class<? extends Action>> actions = new ConcurrentHashMap<>();

    static {
        actions.put("/", MainAction.class);
        actions.put("/index", MainAction.class);
        actions.put("/login", LoginAction.class);
        actions.put("/logout", LogoutAction.class);
        actions.put("/changeLanguage", ChangeLanguageAction.class);
        actions.put("/register", RegistrationAction.class);

        actions.put("/menu/exercises", ShowExercisesAction.class);
        actions.put("/menu/coaches", ShowCoachesAction.class);
        actions.put("/menu/prices", ShowTicketsAction.class);

        actions.put("/authorized_user/groupsByExercise", ShowGroupsByExercise.class);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest)request;
            String contextPath = httpRequest.getContextPath();
            String uri = httpRequest.getRequestURI();
            logger.debug(String.format("Starting of processing of request for URI \"%s\"", uri));
            int endAction = uri.lastIndexOf('.');
            String actionName;
            if (endAction >= 0) {
                actionName = uri.substring(0, endAction);
            } else {
                actionName = uri;
            }
            logger.debug(actionName);
            Class<? extends Action> actionClass = actions.get(actionName);
            try {
                Action action = actionClass.newInstance();
                action.setName(actionName);
                httpRequest.setAttribute("action", action);
                chain.doFilter(request, response);
            } catch (InstantiationException | IllegalAccessException | NullPointerException e) {
                logger.error("It is impossible to create action handler object", e);
                httpRequest.setAttribute("error", String.format("Запрошенный адрес %s не может быть обработан сервером", uri));
                httpRequest.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            }
        } else {
            logger.error("It is impossible to use HTTP filter");
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {}
}
