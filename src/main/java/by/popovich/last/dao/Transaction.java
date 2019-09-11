package by.popovich.last.dao;

import by.popovich.last.exception.PersistentException;

/**
 * Transaction interface.
 */
public interface Transaction {
    /**
     * Read dao by entity class.
     *
     * @param key    entity class.
     * @param <Type> entity.
     * @return DAO of entity class.
     * @throws PersistentException if error in DB handling.
     */
    <Type extends DAO<?>> Type createDao(Class<Type> key)
      throws PersistentException;

    /**
     * Commit.
     *
     * @throws PersistentException if error in DB handling.
     */
    void commit() throws PersistentException;

    /**
     * Rollback.
     *
     * @throws PersistentException if error in DB handling.
     */
    void rollback() throws PersistentException;
}
