package by.popovich.last.dao;

import by.popovich.last.entity.Person;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;

import java.io.InputStream;
import java.util.List;

public interface UserInfoDao extends DAO<Person> {
    List<Person> read() throws PersistentException;

    byte[] readImage(final Integer userId) throws PersistentException;

    void update (Person person, InputStream inputStream) throws PersistentException;
}
