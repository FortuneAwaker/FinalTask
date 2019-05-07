package by.popovich.last.dao;

import by.popovich.last.entity.Person;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface UserInfoDao extends DAO<Person> {
    List<Person> read() throws PersistentException;
}
