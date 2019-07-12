package by.popovich.last.service;

import by.popovich.last.dao.SubscriptionDao;
import by.popovich.last.dao.mysql.SubscriptionDaoImpl;
import by.popovich.last.dao.pool.ConnectionPool;
import by.popovich.last.entity.Subscription;
import by.popovich.last.exception.PersistentException;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class SubscriptionServiceImpl extends ServiceImpl
        implements SubscriptionService{

    @Override
    public List<Subscription> readAll() throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        SubscriptionDao dao = new SubscriptionDaoImpl();
        ((SubscriptionDaoImpl) dao).setConnection(connection);
        return dao.readAll();
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
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        SubscriptionDao dao = new SubscriptionDaoImpl();
        ((SubscriptionDaoImpl) dao).setConnection(connection);
        dao.delete(identity);
    }

    @Override
    public List<Subscription> readByGroupId(Integer groupId) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        SubscriptionDao dao = new SubscriptionDaoImpl();
        ((SubscriptionDaoImpl) dao).setConnection(connection);
        return dao.readByGroupId(groupId);
    }

    @Override
    public List<Subscription> readByLastDay(Date lastDayOfSubscription) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        SubscriptionDao dao = new SubscriptionDaoImpl();
        ((SubscriptionDaoImpl) dao).setConnection(connection);
        return dao.readByLastDay(lastDayOfSubscription);
    }

    @Override
    public List<Subscription> readSubscriptionsByClientId(Integer clientId) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        SubscriptionDao dao = new SubscriptionDaoImpl();
        ((SubscriptionDaoImpl) dao).setConnection(connection);
        return dao.readSubscriptionsByClientId(clientId);
    }

    @Override
    public void deleteSubscription(Integer clientId, Integer groupId) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        SubscriptionDao dao = new SubscriptionDaoImpl();
        ((SubscriptionDaoImpl) dao).setConnection(connection);
        dao.deleteSubscription(clientId, groupId);
    }

    @Override
    public Subscription readById(Integer id) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        SubscriptionDao dao = new SubscriptionDaoImpl();
        ((SubscriptionDaoImpl) dao).setConnection(connection);
        return dao.read(id);
    }
}
