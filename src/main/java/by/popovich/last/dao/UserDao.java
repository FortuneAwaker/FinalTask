package by.popovich.last.dao;

import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;

import java.util.List;

/**
 * DAO of user.
 */
public interface UserDao extends DAO<User> {
    /**
     * By login and password.
     *
     * @param login    login.
     * @param password password.
     * @return user from DB.
     * @throws PersistentException if error in DB handling.
     */
    User read(String login, String password) throws PersistentException;

    /**
     * Read all table.
     *
     * @return list of users.
     * @throws PersistentException if error in DB handling.
     */
    List<User> readAll() throws PersistentException;
}
