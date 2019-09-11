package by.popovich.last.service;

import by.popovich.last.entity.Price;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface PriceService extends Service {
    Price readById(Integer identity) throws PersistentException;

    List<Price> readAll() throws PersistentException;

    List<Price> readByExerciseTypeId(final Integer type) throws PersistentException;

    List<Price> readByExerciseTypeName(final String name) throws PersistentException;

    void save(final Price price) throws PersistentException;

    void delete(final Integer identity) throws PersistentException;
}
