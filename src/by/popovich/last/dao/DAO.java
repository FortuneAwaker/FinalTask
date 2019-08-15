package by.popovich.last.dao;

import by.popovich.last.entity.Entity;
import by.popovich.last.exception.PersistentException;

/**
 * Interface for DAO.
 *
 * @param <Type> entity.
 */
public interface DAO<Type extends Entity> {
    /**
     * Create record.
     *
     * @param entity instance.
     * @return id of new instance.
     * @throws PersistentException if error in DB handling.
     */
    Integer create(Type entity) throws PersistentException;

    /**
     * Read by id.
     *
     * @param identity id.
     * @return instance of entity.
     * @throws PersistentException if error in DB handling.
     */
    Type read(Integer identity) throws PersistentException;

    /**
     * Update record.
     *
     * @param entity instance.
     * @throws PersistentException if error in DB handling.
     */
    void update(Type entity) throws PersistentException;

    /**
     * Delete record by id.
     *
     * @param identity id.
     * @throws PersistentException if error in DB handling.
     */
    void delete(Integer identity) throws PersistentException;
}
