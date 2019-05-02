package by.popovich.last.dao;

import by.popovich.last.entity.Entity;
import by.popovich.last.exception.PersistentException;

public interface DAO<Type extends Entity> {
    Integer create(Type entity) throws PersistentException;

    Type read(Integer identity) throws PersistentException;

    void update(Type entity) throws PersistentException;

    void delete(Integer identity) throws PersistentException;
}
