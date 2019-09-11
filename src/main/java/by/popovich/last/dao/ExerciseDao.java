package by.popovich.last.dao;

import by.popovich.last.entity.Exercise;
import by.popovich.last.exception.PersistentException;

import java.util.List;

/**
 * Exercise DAO interface.
 */
public interface ExerciseDao extends DAO<Exercise> {
    /**
     * Read all table.
     *
     * @return list of Exercise.
     * @throws PersistentException if error in DB handling.
     */
    List<Exercise> read() throws PersistentException;

    /**
     * Read Exercise by name of it.
     *
     * @param name name of exercise.
     * @return insatnce of Exercise.
     * @throws PersistentException if error in DB handling.
     */
    Exercise readIdByName(String name) throws PersistentException;
}
