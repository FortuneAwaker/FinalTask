package by.popovich.last.service;

import by.popovich.last.entity.Subscription;
import by.popovich.last.exception.PersistentException;

import java.sql.Date;
import java.util.List;

public interface SubscriptionService extends Service {
    List<Subscription> readAll() throws PersistentException;

    void save(Subscription subscription) throws PersistentException;

    void delete(Integer identity) throws PersistentException;

    List<Subscription> readByGroupId(Integer groupId)
            throws PersistentException;

    List<Subscription> readByLastDay(Date lastDayOfSubscription)
            throws PersistentException;

    List<Subscription> readSubscriptionsByClientId(final Integer clientId)
            throws PersistentException;

    void deleteSubscription(Integer clientId, Integer groupId)
            throws PersistentException;

    Subscription readById(final Integer id) throws PersistentException;
}
