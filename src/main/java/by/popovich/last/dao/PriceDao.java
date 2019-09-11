package by.popovich.last.dao;

import by.popovich.last.entity.Price;
import by.popovich.last.exception.PersistentException;

import java.util.List;

/**
 * Price DAO interface.
 */
public interface PriceDao extends DAO<Price> {
    /**
     * Read list of prices by exercise id.
     * @param type id.
     * @return list of prices.
     * @throws PersistentException if error in DB handling.
     */
    List<Price> readByExerciseType(Integer type) throws PersistentException;

    /**
     * Reads all table.
     * @return list of exercise.
     * @throws PersistentException if error in DB handling.
     */
    List<Price> read() throws PersistentException;
}
