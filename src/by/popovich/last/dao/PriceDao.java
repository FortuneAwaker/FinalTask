package by.popovich.last.dao;

import by.popovich.last.entity.Price;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface PriceDao extends DAO<Price> {
    List<Price> readByExerciseType(Integer type) throws PersistentException;

    List<Price> read() throws PersistentException;
}
