package by.popovich.last.action.admin;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Price;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.PriceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

public class EditPriceAction extends AuthorizedUserAction {
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
        String priceIdFromRequest = request.getParameter("priceId");
        if (priceIdFromRequest != null) {
            session.setAttribute("idOfPriceToEdit", priceIdFromRequest);
        }
        if (!todo.equals("false")) {
            if (priceIdFromRequest != null) {
                Integer priceIdNumber = Integer.parseInt(priceIdFromRequest);
                if (currentUser.getRole().equals(Role.ADMINISTRATOR)) {
                    String numberOfVisitsString = request.getParameter("numberOfVisits");
                    String numberOfDaysString = request.getParameter("numberOfDays");
                    String amountOfMoneyString = request.getParameter("money");
                    if (numberOfVisitsString != null
                            && numberOfDaysString != null
                            && amountOfMoneyString != null) {
                        Integer numberOfVisits = Integer.parseInt(numberOfVisitsString);
                        Integer numberOfDays = Integer.parseInt(numberOfDaysString);
                        Integer amountOfMoney = Integer.parseInt(amountOfMoneyString);
                        PriceService service = serviceFactory.getService(PriceService.class);
                        Price priceToEdit = service.readById(priceIdNumber);
                        if (numberOfDays <= 0 && numberOfVisits <= 0
                                && amountOfMoney <= 0) {
                            request.setAttribute("message",
                                    "Неверные параметры!");
                        } else {
                            priceToEdit.setNumberOfDays(numberOfDays);
                            priceToEdit.setNumberOfVisits(numberOfVisits);
                            priceToEdit.setPrice(amountOfMoney);
                            service.save(priceToEdit);
                            request.setAttribute("message", "Расценка была изменена!");
                            return new Forward("/index.jsp", false);
                        }
                    } else {
                        request.setAttribute("message", "Недопустимое действие");
                        return new Forward("/index.jsp", false);
                    }
                } else {
                    request.setAttribute("message", "Недопустимая для действия роль");
                    return new Forward("/index.jsp", false);
                }
            } else {
                request.setAttribute("message", "Недопустимое действие");
            }
        }
        return null;
    }
}
