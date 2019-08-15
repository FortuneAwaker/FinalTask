package by.popovich.last.dao;

import by.popovich.last.entity.Person;
import by.popovich.last.exception.PersistentException;

import java.io.InputStream;
import java.util.List;

/**
 * User info DAO.
 */
public interface UserInfoDao extends DAO<Person> {
    /**
     * Read all table.
     *
     * @return list of user info.
     * @throws PersistentException if error in DB handling.
     */
    List<Person> read() throws PersistentException;

    /**
     * Read BLOB image from DB.
     *
     * @param userId id of user.
     * @return byte array.
     * @throws PersistentException if error in DB handling.
     */
    byte[] readImage(Integer userId) throws PersistentException;

    /**
     * Updates user info with image.
     *
     * @param person      user info.
     * @param inputStream image.
     * @throws PersistentException if error in DB handling.
     */
    void update(Person person, InputStream inputStream)
      throws PersistentException;
}
