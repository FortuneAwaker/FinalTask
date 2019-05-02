package by.popovich.last.dao;

import by.popovich.last.exception.PersistentException;

public interface Transaction {
    <Type extends DAO<?>> Type createDao(Class<Type> key) throws PersistentException;

    void commit() throws PersistentException;

    void rollback() throws PersistentException;
}
