package by.popovich.last.service;

import by.popovich.last.entity.Person;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface UserInfoService extends Service {
    Person readById(Integer identity) throws PersistentException;

    List<Person> readAll() throws PersistentException;

    void addInfo(Person exercise) throws PersistentException;

    void updateInfo(Person exercise) throws PersistentException;

    void delete(Integer identity) throws PersistentException;
}
