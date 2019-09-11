package by.popovich.last.service;

import by.popovich.last.entity.Exercise;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface ExerciseService extends Service {
    Exercise readIdByName(String name) throws PersistentException;

    Exercise readById(Integer identity) throws PersistentException;

    List<Exercise> readAll() throws PersistentException;

    void save(Exercise exercise) throws PersistentException;

    void delete(Integer identity) throws PersistentException;
}
