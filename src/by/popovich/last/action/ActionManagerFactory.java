package by.popovich.last.action;

import by.popovich.last.service.ServiceFactory;

/**
 * Creates action manager implementation.
 */
public class ActionManagerFactory {
    /**
     * Gets service manager.
     * @param factory service factory.
     * @return implementation of action manager.
     */
    public static ActionManager getManager(final ServiceFactory factory) {
        return new ActionManagerImpl(factory);
    }
}
