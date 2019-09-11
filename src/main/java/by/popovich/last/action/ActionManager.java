package by.popovich.last.action;

import by.popovich.last.exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action manager interface.
 */
public interface ActionManager {
    /**
     * Manages action class to execute action.
     *
     * @param action   action from uri.
     * @param request  HttpServletRequest.
     * @param response HttpServletResponse.
     * @return uri to go.
     * @throws PersistentException if error in DB handling.
     */
    Forward execute(Action action, HttpServletRequest request,
                    HttpServletResponse response) throws PersistentException;

    /**
     * Closes manager.
     */
    void close();
}
