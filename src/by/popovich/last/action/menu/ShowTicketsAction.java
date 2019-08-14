package by.popovich.last.action.menu;

import by.popovich.last.action.Action;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Exercise;
import by.popovich.last.entity.Group;
import by.popovich.last.entity.Price;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.ExerciseService;
import by.popovich.last.service.GroupService;
import by.popovich.last.service.PriceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

public class ShowTicketsAction extends Action {
    /**
     * Logger for creation notes to some appender.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ShowTicketsAction.class);

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
        Integer groupId = (Integer) session.getAttribute("groupId");
        List<Price> prices;
        if (groupId == null) {
            PriceService service = serviceFactory.getService(PriceService.class);
            prices = service.readAll();
            ExerciseService exerciseService = serviceFactory
                    .getService(ExerciseService.class);
            List<Exercise> exercises = exerciseService.readAll();
            for (Price price : prices
            ) {
                for (Exercise exercise : exercises
                ) {
                    if (price.getTypeOfExercise() == exercise.getIdentity()) {
                        price.setNameOfExercise(exercise.getTypeOfExercises());
                        break;
                    }
                }
            }
        } else {
            GroupService groupService = serviceFactory.getService(GroupService.class);
            PriceService priceService = serviceFactory.getService(PriceService.class);
            ExerciseService exerciseService = serviceFactory
                    .getService(ExerciseService.class);
            Exercise exercise = exerciseService.readById(groupService.readById(groupId).getTypeOfExercisesId());
            prices = priceService.readByExerciseTypeId(
                    exercise.getIdentity());
            for (Price price: prices
                 ) {
                price.setNameOfExercise(exercise.getTypeOfExercises());
            }
        }
        if (prices != null) {
            if (request.getAttribute("listOfPrices") == null) {
                request.setAttribute("listOfPrices", prices);
                LOGGER.info("Список цен был успешно показан!");
            }
        }
        return new Forward("/menu/prices.jsp", false);

    }
}
