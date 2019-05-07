package by.popovich.last.dao;

import by.popovich.last.entity.Subscription;
import by.popovich.last.exception.PersistentException;

import java.sql.Date;
import java.util.List;

public interface SubscriptionDao extends DAO<Subscription> {
    List<Subscription> readByGroupId(Integer groupId)
            throws PersistentException;

    List<Subscription> readByLastDay(Date lastDayOfSubscription)
            throws PersistentException;

    List<Subscription> readSubscriptions() throws PersistentException;

    List<Subscription> readSubscriptionsById(Integer identity)
            throws PersistentException;

    void deleteSubscription(Integer id, Integer groupId)
            throws PersistentException;

}
