package by.popovich.last.dao;

import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface UserDao extends DAO<User> {
    User read(String login, String password) throws PersistentException;

    List<User> readAll() throws PersistentException;
}
