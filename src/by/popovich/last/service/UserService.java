package by.popovich.last.service;

import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface UserService extends Service {
    List<User> findAll() throws PersistentException;

    User findByIdentity(Integer identity) throws PersistentException;

    User findByLoginAndPassword(String login, String password) throws PersistentException;

    void save(User user) throws PersistentException;

    void delete(Integer identity) throws PersistentException;
}
