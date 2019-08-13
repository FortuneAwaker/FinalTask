package by.popovich.last.service;

import by.popovich.last.dao.SubscriptionDao;
import by.popovich.last.dao.mysql.SubscriptionDaoImpl;
import by.popovich.last.dao.pool.ConnectionPool;
import by.popovich.last.entity.Subscription;
import by.popovich.last.exception.PersistentException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class SubscriptionServiceImpl extends ServiceImpl
        implements SubscriptionService{

    @Override
    public List<Subscription> readAll() throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        SubscriptionDao dao = new SubscriptionDaoImpl();
        ((SubscriptionDaoImpl) dao).setConnection(connection);
        List<Subscription> subscriptions = dao.readAll();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }

    @Override
    public void save(Subscription subscription) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        SubscriptionDao dao = new SubscriptionDaoImpl();
        ((SubscriptionDaoImpl) dao).setConnection(connection);
        System.out.println(subscription.getIdentity());
        if (subscription.getIdentity() != null) {
            dao.update(subscription);
        } else {
            subscription.setIdentity(dao.create(subscription));
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
        SubscriptionDao dao = new SubscriptionDaoImpl();
        ((SubscriptionDaoImpl) dao).setConnection(connection);
        dao.delete(identity);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Subscription> readByGroupId(Integer groupId) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        SubscriptionDao dao = new SubscriptionDaoImpl();
        ((SubscriptionDaoImpl) dao).setConnection(connection);
        List<Subscription> subscriptions = dao.readByGroupId(groupId);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }

    @Override
    public List<Subscription> readByLastDay(Date lastDayOfSubscription) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        SubscriptionDao dao = new SubscriptionDaoImpl();
        ((SubscriptionDaoImpl) dao).setConnection(connection);
        List<Subscription> subscriptions = dao.readByLastDay(lastDayOfSubscription);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }

    @Override
    public List<Subscription> readSubscriptionsByClientId(Integer clientId) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        SubscriptionDao dao = new SubscriptionDaoImpl();
        ((SubscriptionDaoImpl) dao).setConnection(connection);
        List<Subscription> subscriptions = dao.readSubscriptionsByClientId(clientId);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }

    @Override
    public void deleteSubscription(Integer clientId, Integer groupId) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        SubscriptionDao dao = new SubscriptionDaoImpl();
        ((SubscriptionDaoImpl) dao).setConnection(connection);
        dao.deleteSubscription(clientId, groupId);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Subscription readById(Integer id) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        SubscriptionDao dao = new SubscriptionDaoImpl();
        ((SubscriptionDaoImpl) dao).setConnection(connection);
        Subscription subscription = dao.read(id);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscription;
    }
}
