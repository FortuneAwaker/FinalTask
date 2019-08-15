package by.popovich.last.dao.mysql;

import by.popovich.last.dao.SubscriptionDao;
import by.popovich.last.entity.Subscription;
import by.popovich.last.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Subscription DAO impl.
 */
public class SubscriptionDaoImpl extends BaseDaoImpl
  implements SubscriptionDao {
    /**
     * Three.
     */
    private final int three = 3;
    /**
     * Four.
     */
    private final int four = 4;
    /**
     * Five.
     */
    private final int five = 5;
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
      .getLogger(ExerciseDaoImpl.class);
    /**
     * Read table sql.
     */
    private static final String READ_TABLE = "SELECT * FROM `subscription`";
    /**
     * Read by client id sql.
     */
    private static final String READ_SUBSCRIPTIONS_BY_CLIENT_ID
      = "SELECT * FROM subscription"
      + " WHERE `client_id` = ?";
    /**
     * Read by id sql.
     */
    private static final String READ_SUBSCRIPTION_BY_ID
      = "SELECT * FROM subscription"
      + " WHERE `id` = ?";
    /**
     * Read by group id sql.
     */
    private static final String READ_SUBSCRIPTION_BY_GROUP_ID = "SELECT * FROM "
      + "subscription WHERE `id_of_group` = ?";
    /**
     * Read by last day.
     */
    private static final String READ_SUBSCRIPTION_BY_LAST_DAY
      = "SELECT * FROM subscription WHERE last_day = ?";
    /**
     * Add sql.
     */
    private static final String ADD_SUBSCRIPTION = "INSERT INTO `subscription` "
      + "(client_id, id_of_group, left_visits, last_day, payment)"
      + " VALUES (?, ?, ?, ?, ?)";
    /**
     * Delete by client and group id sql.
     */
    private static final String DELETE_BY_CLIENT_ID = "DELETE FROM "
      + "`subscription` WHERE `client_id` = ? AND `id_of_group` = ?";
    /**
     * Delete by id sql.
     */
    private static final String DELETE_BY_ID = "DELETE FROM "
      + "`subscription` WHERE `id` = ?";
    /**
     * Update sql.
     */
    private static final String UPDATE_SUBSCRIPTION
      = "UPDATE `subscription` SET "
      + "`left_visits` = ?, `last_day` = ?, `payment` = ?"
      + " WHERE `id` = ?";

    /**
     * Read by group id.
     *
     * @param groupId id.
     * @return list of subs.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public List<Subscription> readByGroupId(final Integer groupId)
      throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection
              .prepareStatement(READ_SUBSCRIPTION_BY_GROUP_ID);
            statement.setInt(1, groupId);
            resultSet = statement.executeQuery();
            List<Subscription> subscriptions = new ArrayList<>();
            Subscription subscription;
            while (resultSet.next()) {
                subscription = new Subscription();
                subscription.setIdentity(resultSet
                  .getInt("id"));
                subscription.setClientId(resultSet
                  .getInt("client_id"));
                subscription.setIdOfGroup(groupId);
                subscription.setLastDay(resultSet
                  .getDate("last_day"));
                subscription.setLeftVisits(resultSet
                  .getInt("left_visits"));
                subscription.setPayment(resultSet
                  .getDouble("payment"));
                subscriptions.add(subscription);
            }
            return subscriptions;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException ignored) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException ignored) {
            }
        }
    }

    /**
     * Read by last day.
     *
     * @param lastDayOfSubscription last day.
     * @return list of subs.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public List<Subscription> readByLastDay(final Date lastDayOfSubscription)
      throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection
              .prepareStatement(READ_SUBSCRIPTION_BY_LAST_DAY);
            statement.setDate(1, lastDayOfSubscription);
            resultSet = statement.executeQuery();
            List<Subscription> subscriptions = new ArrayList<>();
            Subscription subscription;
            while (resultSet.next()) {
                subscription = new Subscription();
                subscription.setIdentity(resultSet
                  .getInt("id"));
                subscription.setClientId(resultSet
                  .getInt("client_id"));
                subscription.setIdOfGroup(resultSet
                  .getInt("id_of_group"));
                subscription.setLastDay(lastDayOfSubscription);
                subscription.setLeftVisits(resultSet
                  .getInt("left_visits"));
                subscription.setPayment(resultSet
                  .getDouble("payment"));
                subscriptions.add(subscription);
            }
            return subscriptions;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException ignored) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException ignored) {
            }
        }
    }

    /**
     * Read all.
     *
     * @return list of subs.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public List<Subscription> readAll() throws PersistentException {
        try (PreparedStatement statement = connection
          .prepareStatement(READ_TABLE);
             ResultSet resultSet = statement.executeQuery()) {
            List<Subscription> subscriptions = new ArrayList<>();
            Subscription subscription;
            while (resultSet.next()) {
                subscription = new Subscription();
                subscription.setIdentity(resultSet
                  .getInt("id"));
                subscription.setIdOfGroup(resultSet
                  .getInt("id_of_group"));
                subscription.setClientId(resultSet
                  .getInt("client_id"));
                subscription.setLeftVisits(resultSet
                  .getInt("left_visits"));
                subscription.setLastDay(resultSet
                  .getDate("last_day"));
                subscription.setPayment(resultSet
                  .getDouble("payment"));
                subscriptions.add(subscription);
            }
            return subscriptions;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Read by client id.
     *
     * @param identity
     * @return list of subs.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public List<Subscription> readSubscriptionsByClientId(
      final Integer identity)
      throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection
              .prepareStatement(READ_SUBSCRIPTIONS_BY_CLIENT_ID);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            List<Subscription> subscriptions = new ArrayList<>();
            Subscription subscription;
            while (resultSet.next()) {
                subscription = new Subscription();
                subscription.setClientId(identity);
                subscription.setIdOfGroup(resultSet
                  .getInt("id_of_group"));
                subscription.setLastDay(resultSet
                  .getDate("last_day"));
                subscription.setLeftVisits(resultSet
                  .getInt("left_visits"));
                subscription.setIdentity(resultSet
                  .getInt("id"));
                subscription.setPayment(resultSet
                  .getDouble("payment"));
                subscriptions.add(subscription);
            }
            return subscriptions;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException ignored) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException ignored) {
            }
        }
    }

    /**
     * Delete by client and group id.
     *
     * @param clientId client id.
     * @param groupId  group id.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public void deleteSubscription(final Integer clientId,
                                   final Integer groupId)
      throws PersistentException {
        try (PreparedStatement statement
               = connection.prepareStatement(DELETE_BY_CLIENT_ID)) {
            statement.setInt(2, groupId);
            statement.setInt(1, clientId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Create.
     *
     * @param subscription new sub.
     * @return id of sub.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public Integer create(final Subscription subscription)
      throws PersistentException {
        ResultSet resultSet = null;
        try (PreparedStatement statement
               = connection.prepareStatement(ADD_SUBSCRIPTION,
          Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, subscription.getClientId());
            statement.setInt(2, subscription.getIdOfGroup());
            statement.setInt(three, subscription.getLeftVisits());
            statement.setDate(four, subscription.getLastDay());
            statement.setDouble(five, subscription.getPayment());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            System.out.println("created");
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                LOGGER.error("There is no autoincremented "
                  + "index after trying to"
                  + " add record into table `subscription`");
                throw new PersistentException();
            }
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                assert resultSet != null;
                resultSet.close();
            } catch (SQLException | NullPointerException ignored) {
            }
        }
    }

    /**
     * Read by id.
     *
     * @param identity id.
     * @return sub.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public Subscription read(final Integer identity)
      throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_SUBSCRIPTION_BY_ID);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            Subscription subscription = null;
            if (resultSet.next()) {
                subscription = new Subscription();
                subscription.setIdentity(identity);
                subscription.setClientId(resultSet
                  .getInt("client_id"));
                subscription.setIdOfGroup(resultSet
                  .getInt("id_of_group"));
                subscription.setLastDay(resultSet
                  .getDate("last_day"));
                subscription.setLeftVisits(resultSet
                  .getInt("left_visits"));
                subscription.setPayment(resultSet
                  .getDouble("payment"));
            }
            return subscription;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException ignored) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException ignored) {
            }
        }
    }

    /**
     * Update.
     *
     * @param subscription sub.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public void update(final Subscription subscription)
      throws PersistentException {
        try (PreparedStatement statement = connection
          .prepareStatement(UPDATE_SUBSCRIPTION)) {
            statement.setInt(1, subscription.getLeftVisits());
            statement.setDate(2, subscription.getLastDay());
            statement.setDouble(three, subscription.getPayment());
            statement.setInt(four, subscription.getIdentity());
            statement.executeUpdate();
            System.out.println("updated");
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Delete by id.
     *
     * @param identity id.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public void delete(final Integer identity) throws PersistentException {
        try (PreparedStatement statement
               = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, identity);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }
}
