package by.popovich.last.dao;

import by.popovich.last.exception.PersistentException;

public interface TransactionFactory {
    Transaction createTransaction() throws PersistentException;

    void close();
}
