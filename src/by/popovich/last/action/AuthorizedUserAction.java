package by.popovich.last.action;

import by.popovich.last.entity.Role;

import java.util.Arrays;

/**
 * Class for actions wirh authorized user.
 */
public abstract class AuthorizedUserAction extends Action {
    /**
     * Default constructor.
     */
    public AuthorizedUserAction() {
        getRoles().addAll(Arrays.asList(Role.values()));
    }
}
