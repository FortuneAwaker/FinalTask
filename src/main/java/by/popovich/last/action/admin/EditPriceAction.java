package by.popovich.last.action.admin;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Price;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.PriceService;
import by.popovich.last.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

/**
 * Edit price action class.
 */
public class EditPriceAction extends AuthorizedUserAction {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
            .getLogger(EditPriceAction.class);
    /**
     * One.
     */
    private final int one = 1;
    /**
     * Three.
     */
    private final int three = 3;
    /**
     * Six.
     */
    private final int six = 6;

    /**
     * Execute action.
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
            request.setAttribute("message",
                    "Войдите в аккаунт!");
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
                    String numberOfVisitsString = request
                            .getParameter("numberOfVisits");
                    String numberOfDaysString = request
                            .getParameter("numberOfDays");
                    String amountOfMoneyString = request
                            .getParameter("money");
                    if (numberOfVisitsString != null
                            && numberOfDaysString != null
                            && amountOfMoneyString != null) {
                        Validator validator = new Validator();
                        if (!(validator.validateNumber(
                                numberOfDaysString, one, three)
                                && validator.validateNumber(
                                amountOfMoneyString, one, six)
                                && validator.validateNumber(
                                numberOfVisitsString, one, three))) {
                            LOGGER.info("Некорректные данные!");
                            request.setAttribute("message",
                                    "Некорректные данные!");
                            return null;
                        }
                        Integer numberOfVisits = Integer
                                .parseInt(numberOfVisitsString);
                        Integer numberOfDays = Integer
                                .parseInt(numberOfDaysString);
                        Integer amountOfMoney = Integer
                                .parseInt(amountOfMoneyString);
                        PriceService service = serviceFactory
                                .getService(PriceService.class);
                        Price priceToEdit = service.readById(priceIdNumber);
                        if (numberOfDays <= 0 && numberOfVisits <= 0
                                && amountOfMoney <= 0) {
                            LOGGER.info("Неверные параметры!");
                            request.setAttribute("message",
                                    "Неверные параметры!");
                        } else {
                            priceToEdit.setNumberOfDays(numberOfDays);
                            priceToEdit.setNumberOfVisits(numberOfVisits);
                            priceToEdit.setPrice(amountOfMoney);
                            service.save(priceToEdit);
                            LOGGER.info("Расценка была изменена!");
                            request.setAttribute("message",
                                    "Расценка была изменена!");
                            return new Forward("/index.jsp",
                                    false);
                        }
                    } else {
                        LOGGER.info("Недопустимое действие!");
                        request.setAttribute("message",
                                "Недопустимое действие!");
                        return new Forward("/index.jsp",
                                false);
                    }
                } else {
                    LOGGER.info("Недопустимая для действия роль!");
                    request.setAttribute("message",
                            "Недопустимая для действия роль!");
                    return new Forward("/index.jsp",
                            false);
                }
            } else {
                LOGGER.info("Недопустимое действие!");
                request.setAttribute("message",
                        "Недопустимое действие!");
            }
        }
        return null;
    }
}
