package by.popovich.last.dao;

import by.popovich.last.entity.Subscription;
import by.popovich.last.exception.PersistentException;

import java.util.Date;
import java.util.List;

public interface SubscriptionDao extends DAO<Subscription> {
    Subscription readById(Integer id) throws PersistentException;

    List<Subscription> readByGroupId(Integer groupId)
            throws PersistentException;

    List<Subscription> readByLastDay(Date lastDayOfSubscription)
            throws PersistentException;

    List<Subscription> read() throws PersistentException;
}
