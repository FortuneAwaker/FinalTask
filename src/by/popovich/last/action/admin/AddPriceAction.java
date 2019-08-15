package by.popovich.last.action.admin;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Exercise;
import by.popovich.last.entity.Price;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.ExerciseService;
import by.popovich.last.service.PriceService;
import by.popovich.last.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

/**
 * Add price action class.
 */
public class AddPriceAction extends AuthorizedUserAction {
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
     * Logger.
     */
    private static final Logger LOGGER = LogManager
            .getLogger(AddPriceAction.class);

    /**
     * Executes action.
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
        if (currentUser.getRole().equals(Role.ADMINISTRATOR)) {
            ExerciseService service = serviceFactory
                    .getService(ExerciseService.class);
            List<Exercise> exercises = service.readAll();
            session.setAttribute("exercisesToChoose", exercises);
        } else {
            request.setAttribute("message",
                    "Недопустимая для действия роль");
            return new Forward("/index.jsp", false);
        }
        if (!todo.equals("false")) {
            String exerciseId = request.getParameter("chosenExercise");
            String numberOfVisitsString = request.getParameter(
                    "numberOfVisits");
            String numberOfDaysString = request.getParameter(
                    "numberOfDays");
            String numberOfMoneyString = request.getParameter(
                    "money");
            if (numberOfDaysString != null && numberOfMoneyString != null
                    && exerciseId != null && numberOfVisitsString != null) {
                Validator validator = new Validator();
                if (!(validator.validateNumber(
                        numberOfDaysString, one, three)
                        && validator.validateNumber(
                        numberOfMoneyString, one, three)
                        && validator.validateNumber(
                        numberOfVisitsString, one, three))) {
                    LOGGER.info("Некорректные данные");
                    request.setAttribute(
                            "message", "Некорректные данные");
                    return null;
                }
                ExerciseService exerciseService = serviceFactory.getService(ExerciseService.class);
                PriceService service = serviceFactory.getService(PriceService.class);
                Exercise exercise = exerciseService.readById(Integer.parseInt(exerciseId));
                Price priceToAdd = new Price();
                if (exercise != null) {
                    priceToAdd.setNameOfExercise(exercise.getTypeOfExercises());
                    priceToAdd.setTypeOfExercise(exercise.getIdentity());
                } else {
                    LOGGER.info("Ошибка получения упражнения!");
                    request.setAttribute("message", "Ошибка получения упражнения!");
                    return null;
                }
                priceToAdd.setNumberOfVisits(Integer.parseInt(numberOfVisitsString));
                priceToAdd.setNumberOfDays(Integer.parseInt(numberOfDaysString));
                priceToAdd.setPrice(Double.parseDouble(numberOfMoneyString));
                try {
                    service.save(priceToAdd);
                    LOGGER.info("Новая расценка была создана!");
                    request.setAttribute("message", "Новая расценка была создана!");
                    return new Forward("/index.jsp", false);
                } catch (PersistentException e) {
                    request.setAttribute("message", "Ошибка создания группы!");
                }
            }
        }
        return null;
    }
}
