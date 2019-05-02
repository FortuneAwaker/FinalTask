package by.popovich.last.dao;

import by.popovich.last.entity.Price;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface PriceDao extends DAO<Price> {
    List<Price> readByPrice(double price) throws PersistentException;

    List<Price> readByNumberOfDays(int numberOfDays) throws PersistentException;

    List<Price> readByNumberOfVisits(int numberOfVisits)
            throws PersistentException;

    List<Price> readByExerciseType(String type) throws PersistentException;

    List<Price> read() throws PersistentException;
}
