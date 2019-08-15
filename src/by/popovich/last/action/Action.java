package by.popovich.last.action;

import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
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

    /**
     * Getter.
     *
     * @return roles.
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Getter.
     *
     * @return authorized user.
     */
    public User getAuthorizedUser() {
        return authorizedUser;
    }

    /**
     * Setter.
     *
     * @param aU for user.
     */
    public void setAuthorizedUser(final User aU) {
        this.authorizedUser = aU;
    }

    /**
     * Getter.
     *
     * @return name of action.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter.
     *
     * @param n name of action.
     */
    public void setName(final String n) {
        this.name = n;
    }

    /**
     * Service factory that contains interfaces and their realizations.
     *
     * @param sF factory.
     */
    public void setServiceFactory(final ServiceFactory sF) {
        this.serviceFactory = sF;
    }

    /**
     * Executes action from uri.
     *
     * @param request  HttpServletRequest.
     * @param response HttpServletResponse
     * @return uri to go.
     * @throws PersistentException if error in DB handling.
     */
    abstract public Forward executeAction(HttpServletRequest request,
                                          HttpServletResponse response)
            throws PersistentException;
}
