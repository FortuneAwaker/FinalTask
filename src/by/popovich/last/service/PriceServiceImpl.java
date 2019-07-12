package by.popovich.last.service;

import by.popovich.last.dao.ExerciseDao;
import by.popovich.last.dao.PriceDao;
import by.popovich.last.dao.mysql.ExerciseDaoImpl;
import by.popovich.last.dao.mysql.PriceDaoImpl;
import by.popovich.last.dao.pool.ConnectionPool;
import by.popovich.last.entity.Exercise;
import by.popovich.last.entity.Price;
import by.popovich.last.exception.PersistentException;

import java.sql.Connection;
import java.util.List;

public class PriceServiceImpl extends ServiceImpl implements PriceService {
    @Override
    public Price readById(Integer identity) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PriceDao dao = new PriceDaoImpl();
        ((PriceDaoImpl) dao).setConnection(connection);
        return dao.read(identity);
    }

    @Override
    public List<Price> readAll() throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PriceDao dao = new PriceDaoImpl();
        ((PriceDaoImpl) dao).setConnection(connection);
        return dao.read();
    }

    @Override
    public List<Price> readByExerciseTypeId(Integer type)
            throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PriceDao dao = new PriceDaoImpl();
        ((PriceDaoImpl) dao).setConnection(connection);
        return dao.readByExerciseType(type);
    }

    @Override
    public List<Price> readByExerciseTypeName(String name) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PriceDao dao = new PriceDaoImpl();
        ((PriceDaoImpl) dao).setConnection(connection);
        ExerciseDao exerciseDao = new ExerciseDaoImpl();
        ((ExerciseDaoImpl) exerciseDao).setConnection(connection);
        Exercise exercise = new Exercise();
        exercise = exerciseDao.readIdByName(name);
        return dao.readByExerciseType(exercise.getIdentity());
    }

    @Override
    public void save(Price price) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PriceDao dao = new PriceDaoImpl();
        ((PriceDaoImpl) dao).setConnection(connection);
        if(price.getIdentity() != null) {
            dao.update(price);
        } else {
            price.setIdentity(dao.create(price));
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PriceDao dao = new PriceDaoImpl();
        ((PriceDaoImpl) dao).setConnection(connection);
        dao.delete(identity);
    }
}
