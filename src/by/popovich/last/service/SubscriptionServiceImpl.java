package by.popovich.last.service;

import by.popovich.last.dao.SubscriptionDao;
import by.popovich.last.entity.Subscription;
import by.popovich.last.exception.PersistentException;

import java.sql.Date;
import java.util.List;

public class SubscriptionServiceImpl extends ServiceImpl
        implements SubscriptionService{
    @Override
    public List<Subscription> readAll() throws PersistentException {
        SubscriptionDao dao = transaction.createDao(SubscriptionDao.class);
        return dao.readSubscriptions();
    }

    @Override
    public void add(Subscription subscription) throws PersistentException {
        SubscriptionDao dao = transaction.createDao(SubscriptionDao.class);
        dao.create(subscription);
    }

    @Override
    public void update(Subscription subscription) throws PersistentException {
        SubscriptionDao dao = transaction.createDao(SubscriptionDao.class);
        dao.update(subscription);
    }

    @Override
    public void delete(final Integer identity,
                       final Integer groupId) throws PersistentException {
        SubscriptionDao dao = transaction.createDao(SubscriptionDao.class);
        dao.deleteSubscription(identity, groupId);
    }

    @Override
    public List<Subscription> readByGroupId(Integer groupId) throws PersistentException {
        SubscriptionDao dao = transaction.createDao(SubscriptionDao.class);
        return dao.readByGroupId(groupId);
    }

    @Override
    public List<Subscription> readByLastDay(Date lastDayOfSubscription) throws PersistentException {
        SubscriptionDao dao = transaction.createDao(SubscriptionDao.class);
        return dao.readByLastDay(lastDayOfSubscription);
    }

    @Override
    public List<Subscription> readSubscriptionsById(Integer identity) throws PersistentException {
        SubscriptionDao dao = transaction.createDao(SubscriptionDao.class);
        return dao.readSubscriptionsById(identity);
    }
}
