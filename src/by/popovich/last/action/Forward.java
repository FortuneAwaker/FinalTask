package by.popovich.last.action;

import java.util.HashMap;
import java.util.Map;

/**
 * Static class Forward for working with attributes.
 */
public class Forward {
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
     * @param forward is forward or redirect.
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
