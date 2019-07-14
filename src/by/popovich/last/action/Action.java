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

    /**
     * Static class Forward for working with attributes.
     */
    public static class Forward {
        /**
         * String of forward.
         */
        private String forward;
        /**
         * Is this redirect or not.
         */
        private boolean redirect;
        /**
         * Map of attributes with name as a key and a context as a value.
         */
        private Map<String, Object> attributes = new HashMap<>();

        /**
         * Constructor with parameters.
         * @param forwardToConstruct forward for constructor.
         * @param isRedirect is it redirected.
         */
        public Forward(final String forwardToConstruct,
                       final boolean isRedirect) {
            this.forward = forwardToConstruct;
            this.redirect = isRedirect;
        }

        /**
         * Constructor with only forward(redirect is true by default)
         * @param forward
         */
        public Forward(String forward) {
            this(forward, true);
        }

        /**
         * Getter for forward String.
         * @return forward.
         */
        public String getForward() {
            return forward;
        }

        /**
         * Setter for forward String.
         * @param forwardToSet new forward.
         */
        public void setForward(final String forwardToSet) {
            this.forward = forward;
        }

        /**
         * "Getter" for boolean field redirect.
         * @return is redirect or not.
         */
        public boolean isRedirect() {
            return redirect;
        }

        /**
         * "Setter" for boolean field.
         * @param isRedirect value to set.
         */
        public void setRedirect(boolean isRedirect) {
            this.redirect = redirect;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }
    }

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

    abstract public Action.Forward executeAction(HttpServletRequest request, HttpServletResponse response) throws PersistentException;
}
