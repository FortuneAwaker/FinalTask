package by.popovich.last.service;

import by.popovich.last.dao.UserInfoDao;
import by.popovich.last.dao.mysql.UserInfoDaoImpl;
import by.popovich.last.dao.pool.ConnectionPool;
import by.popovich.last.entity.Person;
import by.popovich.last.exception.PersistentException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserInfoServiceImpl extends ServiceImpl
        implements UserInfoService {
    @Override
    public Person readById(Integer identity) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserInfoDao dao = new UserInfoDaoImpl();
        ((UserInfoDaoImpl) dao).setConnection(connection);
        Person person = ((UserInfoDaoImpl) dao).read(identity);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public List<Person> readAll() throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserInfoDao dao = new UserInfoDaoImpl();
        ((UserInfoDaoImpl) dao).setConnection(connection);
        List<Person> people = dao.read();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    @Override
    public void addInfo(final Person person) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserInfoDao dao = new UserInfoDaoImpl();
        ((UserInfoDaoImpl) dao).setConnection(connection);
        dao.create(person);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateInfo(final Person person) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserInfoDao dao = new UserInfoDaoImpl();
        ((UserInfoDaoImpl) dao).setConnection(connection);
        dao.update(person);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(final Integer identity) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserInfoDao dao = new UserInfoDaoImpl();
        ((UserInfoDaoImpl) dao).setConnection(connection);
        dao.delete(identity);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
