package by.popovich.last.service;

import by.popovich.last.dao.ExerciseDao;
import by.popovich.last.dao.mysql.ExerciseDaoImpl;
import by.popovich.last.dao.pool.ConnectionPool;
import by.popovich.last.entity.Exercise;
import by.popovich.last.exception.PersistentException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ExerciseServiceImpl extends ServiceImpl
        implements ExerciseService {
    @Override
    public Exercise readIdByName(String name) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        ExerciseDaoImpl dao = new ExerciseDaoImpl();
        dao.setConnection(connection);
        Exercise exercise = dao.readIdByName(name);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercise;
    }

    @Override
    public Exercise readById(Integer identity) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        ExerciseDaoImpl dao = new ExerciseDaoImpl();
        dao.setConnection(connection);
        Exercise exercise = dao.read(identity);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercise;
    }

    @Override
    public List<Exercise> readAll() throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        ExerciseDaoImpl dao = new ExerciseDaoImpl();
        dao.setConnection(connection);
        List<Exercise> exercises = dao.read();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercises;
    }

    @Override
    public void save(Exercise exercise) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        ExerciseDaoImpl dao = new ExerciseDaoImpl();
        dao.setConnection(connection);
        if(exercise.getIdentity() != null) {
            dao.update(exercise);
        } else {
            exercise.setIdentity(dao.create(exercise));
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        ExerciseDaoImpl dao = new ExerciseDaoImpl();
        dao.setConnection(connection);
        dao.delete(identity);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
