package by.popovich.last.dao;

import by.popovich.last.exception.PersistentException;

/**
 * Transaction factory.
 */
public interface TransactionFactory {
    /**
     * Creates transaction.
     * @return transaction.
     * @throws PersistentException if error in DB handling.
     */
    Transaction createTransaction() throws PersistentException;

    /**
     * Closes.
     */
    void close();
}
