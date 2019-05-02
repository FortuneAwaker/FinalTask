package by.popovich.last.dao;

import by.popovich.last.entity.Exercise;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface ExerciseDao extends DAO<Exercise> {
    Exercise readById(Integer id) throws PersistentException;

    List<Exercise> read() throws PersistentException;
}
