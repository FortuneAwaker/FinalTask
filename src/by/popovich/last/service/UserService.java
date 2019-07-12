package by.popovich.last.service;

import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface UserService extends Service {
    List<User> readAll() throws PersistentException;

    User readByIdentity(final Integer identity) throws PersistentException;

    User readByLoginAndPassword(final String login, final String password) throws PersistentException;

    void save(final User user) throws PersistentException;

    void delete(final Integer identity) throws PersistentException;
}
