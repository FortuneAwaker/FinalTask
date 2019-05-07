package by.popovich.last.service;

import by.popovich.last.entity.Price;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface PriceService extends Service {
    Price readById(Integer identity) throws PersistentException;

    List<Price> readAll() throws PersistentException;

    List<Price> readByExerciseType(Integer type) throws PersistentException;

    void save(Price price) throws PersistentException;

    void delete(Integer identity) throws PersistentException;
}
