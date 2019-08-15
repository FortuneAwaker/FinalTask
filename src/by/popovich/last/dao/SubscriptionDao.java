package by.popovich.last.dao;

import by.popovich.last.entity.Subscription;
import by.popovich.last.exception.PersistentException;

import java.sql.Date;
import java.util.List;

/**
 * Subscription DAO interface.
 */
public interface SubscriptionDao extends DAO<Subscription> {
    /**
     * Read list of subs by group id.
     *
     * @param groupId id.
     * @return list of subs.
     * @throws PersistentException if error in DB handling.
     */
    List<Subscription> readByGroupId(Integer groupId)
      throws PersistentException;

    /**
     * Read list of subs by last day.
     *
     * @param lastDayOfSubscription last day.
     * @return list of subs.
     * @throws PersistentException if error in DB handling.
     */
    List<Subscription> readByLastDay(Date lastDayOfSubscription)
      throws PersistentException;

    /**
     * Read all table.
     *
     * @return list of subs.
     * @throws PersistentException if error in DB handling.
     */
    List<Subscription> readAll() throws PersistentException;

    /**
     * Read list of subs by client id.
     *
     * @param clientId id.
     * @return list of subs.
     * @throws PersistentException if error in DB handling.
     */
    List<Subscription> readSubscriptionsByClientId(Integer clientId)
      throws PersistentException;

    /**
     * Delete sub by client and group id.
     *
     * @param clientId client id.
     * @param groupId  group id.
     * @throws PersistentException if error in DB handling.
     */
    void deleteSubscription(Integer clientId, Integer groupId)
      throws PersistentException;

}
