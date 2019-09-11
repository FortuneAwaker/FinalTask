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
import java.sql.SQLException;
import java.util.List;

public class PriceServiceImpl extends ServiceImpl implements PriceService {
    @Override
    public Price readById(Integer identity) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PriceDao dao = new PriceDaoImpl();
        ((PriceDaoImpl) dao).setConnection(connection);
        Price price = ((PriceDaoImpl) dao).read(identity);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    @Override
    public List<Price> readAll() throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PriceDao dao = new PriceDaoImpl();
        ((PriceDaoImpl) dao).setConnection(connection);
        List<Price> prices = dao.read();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prices;
    }

    @Override
    public List<Price> readByExerciseTypeId(Integer type)
            throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PriceDao dao = new PriceDaoImpl();
        ((PriceDaoImpl) dao).setConnection(connection);
        List<Price> prices = dao.readByExerciseType(type);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prices;
    }

    @Override
    public List<Price> readByExerciseTypeName(String name) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PriceDao dao = new PriceDaoImpl();
        ((PriceDaoImpl) dao).setConnection(connection);
        ExerciseDao exerciseDao = new ExerciseDaoImpl();
        ((ExerciseDaoImpl) exerciseDao).setConnection(connection);
        Exercise exercise = exerciseDao.readIdByName(name);
        List<Price> prices = dao.readByExerciseType(exercise.getIdentity());
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prices;
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
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PriceDao dao = new PriceDaoImpl();
        ((PriceDaoImpl) dao).setConnection(connection);
        dao.delete(identity);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
