package by.popovich.last.controller;

import by.popovich.last.action.Action;
import by.popovich.last.action.ChangeLanguageAction;
import by.popovich.last.action.LoginAction;
import by.popovich.last.action.MainAction;
import by.popovich.last.action.RegistrationAction;
import by.popovich.last.action.admin.AddExerciseAction;
import by.popovich.last.action.admin.AddPriceAction;
import by.popovich.last.action.admin.ChangeRoleAction;
import by.popovich.last.action.admin.DeleteExerciseAction;
import by.popovich.last.action.admin.DeletePriceAction;
import by.popovich.last.action.admin.DeleteUserAction;
import by.popovich.last.action.admin.EditExerciseAction;
import by.popovich.last.action.admin.EditPriceAction;
import by.popovich.last.action.admin.ShowAllGroups;
import by.popovich.last.action.admin.ShowAllSubscriptions;
import by.popovich.last.action.admin.ShowAllUsers;
import by.popovich.last.action.authorizedUser.ChangePasswordAction;
import by.popovich.last.action.authorizedUser.LogoutAction;
import by.popovich.last.action.authorizedUser.SaveProfileAction;
import by.popovich.last.action.authorizedUser.ShowGroupsByExercise;
import by.popovich.last.action.authorizedUser.ShowProfileAction;
import by.popovich.last.action.authorizedUser.ShowSubscribtionsAction;
import by.popovich.last.action.authorizedUser.SubscribeAction;
import by.popovich.last.action.coach.AddGroupAction;
import by.popovich.last.action.coach.DeleteGroupAction;
import by.popovich.last.action.coach.DeleteSubscriptionAction;
import by.popovich.last.action.coach.EditGroupAction;
import by.popovich.last.action.coach.NotifyVisitAction;
import by.popovich.last.action.coach.ShowGroupsOfCoach;
import by.popovich.last.action.coach.ShowMembersOfGroup;
import by.popovich.last.action.menu.ShowCoachesAction;
import by.popovich.last.action.menu.ShowExercisesAction;
import by.popovich.last.action.menu.ShowTicketsAction;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Filter for getting action from uri anf calling proper action class.
 */
public class ActionFilter implements Filter {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
      .getLogger(ActionFilter.class);

    /**
     * Actions and their uri.
     */
    private static Map<String, Class<? extends Action>>
      actions = new ConcurrentHashMap<>();

    /**
     * Putting uri and action classes to map.
     */
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

        actions.put("/authorized_user/groupsByExercise",
          ShowGroupsByExercise.class);
        actions.put("/authorized_user/subscribe",
          SubscribeAction.class);
        actions.put("/authorized_user/mySubscriptions",
          ShowSubscribtionsAction.class);
        actions.put("/authorized_user/profile", ShowProfileAction.class);
        actions.put("/authorized_user/saveProfile", SaveProfileAction.class);
        actions.put("/authorized_user/changePassword",
          ChangePasswordAction.class);

        actions.put("/coach/groups", ShowGroupsOfCoach.class);
        actions.put("/coach/membersOfGroup", ShowMembersOfGroup.class);
        actions.put("/coach/deleteSubscribe", DeleteSubscriptionAction.class);
        actions.put("/coach/addGroup", AddGroupAction.class);
        actions.put("/coach/deleteGroup", DeleteGroupAction.class);
        actions.put("/coach/editGroup", EditGroupAction.class);
        actions.put("/coach/notifyVisit", NotifyVisitAction.class);

        actions.put("/admin/addPrice", AddPriceAction.class);
        actions.put("/admin/editPrice", EditPriceAction.class);
        actions.put("/admin/deletePrice", DeletePriceAction.class);
        actions.put("/admin/addExercise", AddExerciseAction.class);
        actions.put("/admin/editExercise", EditExerciseAction.class);
        actions.put("/admin/deleteExercise", DeleteExerciseAction.class);
        actions.put("/admin/allSubscriptions", ShowAllSubscriptions.class);
        actions.put("/admin/allGroups", ShowAllGroups.class);
        actions.put("/admin/allUsers", ShowAllUsers.class);
        actions.put("/admin/deleteUser", DeleteUserAction.class);
        actions.put("/admin/changeRole", ChangeRoleAction.class);

    }

    /**
     * Init filter.
     *
     * @param filterConfig filter configuration.
     * @throws ServletException servlet exception.
     */
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Do filter.
     *
     * @param request  request.
     * @param response response.
     * @param chain    filter chain.
     * @throws IOException      IOException.
     * @throws ServletException servlet exception.
     */
    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain)
      throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String uri = httpRequest.getRequestURI();
            LOGGER.debug(String.format(
              "Starting of processing of request for URI \"%s\"", uri));
            int endAction = uri.lastIndexOf('.');
            String actionName;
            if (endAction >= 0) {
                actionName = uri.substring(0, endAction);
            } else {
                actionName = uri;
            }
            LOGGER.debug(actionName);
            if (!actionName.equals("/authorized_user/subscribe")) {
                HttpSession session = ((HttpServletRequest) request)
                  .getSession(true);
                if (session.getAttribute("priceId") != null) {
                    session.removeAttribute("priceId");
                }
                if (session.getAttribute("groupId") != null) {
                    session.removeAttribute("groupId");
                }
            }
            Class<? extends Action> actionClass = actions.get(actionName);
            try {
                Action action = actionClass.newInstance();
                action.setName(actionName);
                httpRequest.setAttribute("action", action);
                chain.doFilter(request, response);
            } catch (InstantiationException | IllegalAccessException
              | NullPointerException e) {
                LOGGER.error("It is impossible to "
                  + "create action handler object", e);
                httpRequest.setAttribute("error", String.format(
                  "Запрошенный адрес %s не может "
                    + "быть обработан сервером", uri));
                httpRequest.getServletContext().getRequestDispatcher(
                  "/WEB-INF/jsp/error.jsp").forward(request, response);
            }
        } else {
            LOGGER.error("It is impossible to use HTTP filter");
            request.getServletContext().getRequestDispatcher(
              "/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    /**
     * Destroy.
     */
    @Override
    public void destroy() {
    }
}
