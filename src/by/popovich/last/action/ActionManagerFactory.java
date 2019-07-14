package by.popovich.last.action;

import by.popovich.last.service.ServiceFactory;

public class ActionManagerFactory {
    public static ActionManager getManager(ServiceFactory factory) {
        return new ActionManagerImpl(factory);
    }
}
