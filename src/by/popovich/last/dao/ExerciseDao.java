package by.popovich.last.dao;

import by.popovich.last.entity.Exercise;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface ExerciseDao extends DAO<Exercise> {
    List<Exercise> read() throws PersistentException;

    Exercise readIdByName(String name) throws PersistentException;
}
