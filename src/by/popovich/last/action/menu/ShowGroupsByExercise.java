package by.popovich.last.action.menu;

import by.popovich.last.action.AuthorizedUserAction;
import by.popovich.last.action.ChangeLanguageAction;
import by.popovich.last.action.Forward;
import by.popovich.last.entity.Group;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.GroupService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowGroupsByExercise extends AuthorizedUserAction {
    private static Logger logger = Logger.getLogger(ChangeLanguageAction.class);

    @Override
    public Forward executeAction(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        String exercise = request.getParameter("exercise");
        logger.info(exercise);
        GroupService service = serviceFactory.getService(GroupService.class);
        List<Group> groups = service.readGroupsByTypeName(exercise);
        if (groups != null) {
            HttpSession session = request.getSession();
            session.setAttribute("exerciseName", exercise);
            session.setAttribute("groupsByExercise", groups);
            logger.info("Groups with certain exercise were shown.");
            return new Forward("/authorized_user/groupsByExercise.jsp", false);
        } else {
            logger.info("Groups were not found!");
            request.setAttribute("message", "Группы не найдены!");
        }
        return null;
    }
}
