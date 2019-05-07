package by.popovich.last.service;

import by.popovich.last.dao.ExerciseDao;
import by.popovich.last.entity.Exercise;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public class ExerciseServiceImpl extends ServiceImpl
        implements ExerciseService {
    @Override
    public Exercise readById(Integer identity) throws PersistentException {
        ExerciseDao dao = transaction.createDao(ExerciseDao.class);
        return dao.read(identity);
    }

    @Override
    public List<Exercise> readAll() throws PersistentException {
        ExerciseDao dao = transaction.createDao(ExerciseDao.class);
        return dao.read();
    }

    @Override
    public void save(Exercise exercise) throws PersistentException {
        ExerciseDao dao = transaction.createDao(ExerciseDao.class);
        if(exercise.getIdentity() != null) {
            dao.update(exercise);
        } else {
            exercise.setIdentity(dao.create(exercise));
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        ExerciseDao dao = transaction.createDao(ExerciseDao.class);
        dao.delete(identity);
    }
}
