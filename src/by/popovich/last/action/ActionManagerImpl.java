package by.popovich.last.action;

import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implementation of action manager.
 */
public class ActionManagerImpl implements ActionManager {
    /**
     * Service factory.
     */
    private ServiceFactory serviceFactory;

    /**
     * Constructor with parameter.
     *
     * @param factory service factory.
     */
    public ActionManagerImpl(final ServiceFactory factory) {
        this.serviceFactory = factory;
    }

    /**
     * Manages action class to execute action.
     *
     * @param action   action from uri.
     * @param request  HttpServletRequest.
     * @param response HttpServletResponse.
     * @return uri to go.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public Forward execute(final Action action,
                           final HttpServletRequest request,
                           final HttpServletResponse response)
            throws PersistentException {
        action.setServiceFactory(serviceFactory);
        return action.executeAction(request, response);
    }

    /**
     * Closes service factory.
     */
    @Override
    public void close() {
        serviceFactory.close();
    }
}
