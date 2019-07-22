package by.popovich.last.action;

import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Panoptic abstract class for describing  actions.
 */
abstract public class Action {
    /**
     * Available roles.
     */
    private Set<Role> roles = new HashSet<>();
    /**
     * User that wants to commit thus action.
     */
    private User authorizedUser;
    /**
     * Name.
     */
    private String name;

    /**
     * Interface for working with services.
     */
    protected ServiceFactory serviceFactory;

    public Set<Role> getRoles() {
        return roles;
    }

    public User getAuthorizedUser() {
        return authorizedUser;
    }

    public void setAuthorizedUser(User authorizedUser) {
        this.authorizedUser = authorizedUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    abstract public Forward executeAction(HttpServletRequest request, HttpServletResponse response) throws PersistentException;
}
