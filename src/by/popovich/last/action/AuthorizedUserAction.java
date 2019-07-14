package by.popovich.last.action;

import by.popovich.last.entity.Role;

import java.util.Arrays;

public abstract class AuthorizedUserAction extends Action {
    public AuthorizedUserAction() {
        getRoles().addAll(Arrays.asList(Role.values()));
    }
}
