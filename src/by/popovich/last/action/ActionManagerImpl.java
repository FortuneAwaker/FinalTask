package by.popovich.last.action;

import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionManagerImpl implements ActionManager {
    private ServiceFactory serviceFactory;

    public ActionManagerImpl(ServiceFactory factory) {
        this.serviceFactory = factory;
    }

    @Override
    public Action.Forward execute(Action action, HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        action.setServiceFactory(serviceFactory);
        return action.executeAction(request, response);
    }

    @Override
    public void close() {
        serviceFactory.close();
    }
}
