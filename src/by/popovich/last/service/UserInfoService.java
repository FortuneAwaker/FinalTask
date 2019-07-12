package by.popovich.last.service;

import by.popovich.last.entity.Person;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface UserInfoService extends Service {
    Person readById(final Integer identity) throws PersistentException;

    List<Person> readAll() throws PersistentException;

    void addInfo(final Person person) throws PersistentException;

    void updateInfo(final Person person) throws PersistentException;

    void delete(final Integer identity) throws PersistentException;
}
