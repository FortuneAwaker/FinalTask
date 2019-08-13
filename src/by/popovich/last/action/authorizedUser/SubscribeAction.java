package by.popovich.last.action.authorizedUser;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.controller.DispatcherServlet;
import by.popovich.last.entity.*;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.ExerciseService;
import by.popovich.last.service.GroupService;
import by.popovich.last.service.PriceService;
import by.popovich.last.service.SubscriptionService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.sql.Date;
import java.util.List;
import java.util.Locale;

public class SubscribeAction extends AuthorizedUserAction {
    private static Logger logger = Logger.getLogger(DispatcherServlet.class);

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
        if (currentUser == null) {
            request.setAttribute("message", "Войдите в аккаунт!");
            return new Forward("/index.jsp", false);
        }
        if (currentUser.getRole().equals(Role.CLIENT)) {
            String priceId = request.getParameter("priceId");
            String groupId = request.getParameter("groupId");
            logger.info("groupId = " + groupId + ", priceId = " + priceId);
            if (priceId == null && groupId != null) {
                GroupService service = serviceFactory.getService(GroupService.class);
                Integer groupIdNumber = Integer.parseInt(groupId);
                session.setAttribute("groupId", groupIdNumber);
                request.setAttribute("groupId", groupIdNumber);
                Integer priceIdFromSession = (Integer)
                        session.getAttribute("priceId");
                if (priceIdFromSession == null) {
                    Group group = service.readById(groupIdNumber);
                    PriceService priceService = serviceFactory.getService(PriceService.class);
                    ExerciseService exerciseService = serviceFactory.getService(ExerciseService.class);
                    Exercise exercise = exerciseService.readById(group.getTypeOfExercisesId());
                    List<Price> pricesOfGroup = priceService.readByExerciseTypeId(exercise.getIdentity());
                    if (pricesOfGroup != null) {
                        session.setAttribute("pricesOfGroup", pricesOfGroup);
                    }
                    if (exercise != null) {
                        session.setAttribute("wantedExercise", exercise);
                    }
                    return new Forward("/authorized_user/subscribe.jsp", false);
                } else {
                    Group group = service.readById(groupIdNumber);
                    if (group.getCurrentClients() < group.getMaxClients()) {
                        try {
                            subscribe(priceIdFromSession, groupIdNumber, currentUser.getIdentity());
                            service.addVisitor(groupIdNumber);
                            if (session.getAttribute("pricesOfGroup") != null) {
                                session.removeAttribute("pricesOfGroup");
                                session.removeAttribute("wantedExercise");
                            }
                            if (session.getAttribute("groupsOfPrice") != null) {
                                session.removeAttribute("groupsOfPrice");
                            }
                            request.setAttribute("message", "Подписка оформлена успешно!");
                        } catch (PersistentException e) {
                            request.setAttribute("message", "Вы уже состоите в этой группе!");
                        }
                    } else {
                        request.setAttribute("message", "Группа уже укомплектована!");
                    }
                }
            } else if (priceId != null && groupId == null) {
                GroupService service = serviceFactory.getService(GroupService.class);
                Integer priceIdNumber = Integer.parseInt(priceId);
                session.setAttribute("priceId", priceIdNumber);
                request.setAttribute("priceId", priceIdNumber);
                Integer groupIdFromSession = (Integer)
                        session.getAttribute("groupId");
                if (groupIdFromSession == null) {
                    PriceService priceService = serviceFactory.getService(PriceService.class);
                    ExerciseService exerciseService = serviceFactory.getService(ExerciseService.class);
                    Price price = priceService.readById(priceIdNumber);
                    List<Group> groupsOfPrices = service.readGroupsByTypeId(price.getTypeOfExercise());
                    Exercise exercise = exerciseService.readById(price.getTypeOfExercise());
                    if (groupsOfPrices != null) {
                        session.setAttribute("groupsOfPrices", groupsOfPrices);
                    }
                    if (exercise != null) {
                        session.setAttribute("wantedExercise", exercise);
                    }
                    return new Forward("/authorized_user/subscribe.jsp", false);
                } else {
                    Group group = service.readById(groupIdFromSession);
                    if (group.getCurrentClients() < group.getMaxClients()) {
                        try {
                            subscribe(priceIdNumber, groupIdFromSession, currentUser.getIdentity());
                            service.addVisitor(groupIdFromSession);
                            if (session.getAttribute("groupsOfPrice") != null) {
                                session.removeAttribute("groupsOfPrice");
                            }
                            if (session.getAttribute("pricesOfGroup") != null) {
                                session.removeAttribute("pricesOfGroup");
                                session.removeAttribute("wantedExercise");
                            }
                            request.setAttribute("message", "Подписка оформлена успешно!");
                        } catch (PersistentException e) {
                            request.setAttribute("message", "Вы уже состоите в этой группе!");
                            return null;
                        }
                    } else {
                        request.setAttribute("message", "Группа уже укомплектована!");
                        return null;
                    }
                }
            }
        } else {
            request.setAttribute("message", "Недопустимая для действия роль");
            return new Forward("/index.jsp", false);
        }
        return new Forward("/index.jsp", false);
    }

    private void subscribe(final Integer priceId, final Integer groupId, final Integer clientId)
            throws PersistentException {
        SubscriptionService service = serviceFactory.getService(SubscriptionService.class);
        List<Subscription> subscriptionsOfGroup = service.readByGroupId(groupId);
        boolean exist = false;
        for (Subscription sub: subscriptionsOfGroup
             ) {
            if (sub.getClientId() == clientId) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            PriceService priceService = serviceFactory.getService(PriceService.class);
            Price price = priceService.readById(priceId);
            Subscription subscription = new Subscription();
            subscription.setClientId(clientId);
            subscription.setIdOfGroup(groupId);
            subscription.setLeftVisits(price.getNumberOfVisits());
            java.util.Date today = new java.util.Date();
            long milliseconds = today.getTime() + 86400000 * price.getNumberOfDays();
            Date date = new Date(milliseconds);
            subscription.setLastDay(date);
            subscription.setPayment(price.getPrice());
            service.save(subscription);
        } else {
            throw new PersistentException();
        }
    }
}
