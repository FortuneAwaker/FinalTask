package by.popovich.last.action;

import by.popovich.last.exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionManager {
    Action.Forward execute(Action action, HttpServletRequest request, HttpServletResponse response) throws PersistentException;

    void close();
}
