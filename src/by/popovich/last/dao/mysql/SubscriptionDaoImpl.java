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

public class SubscriptionDaoImpl extends BaseDaoImpl
        implements SubscriptionDao {
    private static Logger LOGGER = LogManager.getLogger(ExerciseDaoImpl.class);

    private final String READ_TABLE = "SELECT * FROM `subscription`";

    private final String READ_SUBSCRIPTIONS_BY_ID = "SELECT `id`,"
            + " `id_of_group`, `left_visits`, `last_day` FROM subscription"
            + " WHERE id = ?";

    private final String READ_SUBSCRIPTION_BY_GROUP_ID = "SELECT `id`, "
            + "`id_of_group`, `left_visits`, `last_day` FROM subscription"
            + " WHERE id_of_group = ?";

    private final String READ_SUBSCRIPTION_BY_LAST_DAY
            = "SELECT `id`, `id_of_group`, "
            + "`left_visits`, `last_day` FROM subscription WHERE last_day = ?";

    private final String ADD_SUBSCRIPTION = "INSERT INTO `subscription` "
            + "(id, id_of_group, left_visits, last_day) VALUES (?, ?, ?, ?)";

    private final String DELETE_BY_ID = "DELETE FROM "
            + "`subscription` WHERE `id` = ? AND `id_of_group` = ?";

    private final String UPDATE_SUBSCRIPTION = "UPDATE `subscription` SET "
            + "`left_visits` = ?, `last_day` = ?"
            + " WHERE `id` = ?";

    @Override
    public List<Subscription> readByGroupId(Integer groupId)
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
                subscription.setIdOfGroup(groupId);
                subscription.setLastDay(resultSet
                        .getDate("last_day"));
                subscription.setLeftVisits(resultSet
                        .getInt("left_visits"));
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

    @Override
    public List<Subscription> readByLastDay(Date lastDayOfSubscription)
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
                subscription.setIdOfGroup(resultSet
                        .getInt("id_of_group"));
                subscription.setLastDay(lastDayOfSubscription);
                subscription.setLeftVisits(resultSet
                        .getInt("left_visits"));
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

    @Override
    public List<Subscription> readSubscriptions() throws PersistentException {
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
                subscription.setLeftVisits(resultSet
                        .getInt("left_visits"));
                subscription.setLastDay(resultSet
                        .getDate("last_day"));
                subscriptions.add(subscription);
            }
            return subscriptions;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Subscription> readSubscriptionsById(Integer identity)
            throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_SUBSCRIPTIONS_BY_ID);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            List<Subscription> subscriptions = new ArrayList<>();
            Subscription subscription;
            while (resultSet.next()) {
                subscription = new Subscription();
                subscription.setIdentity(identity);
                subscription.setIdOfGroup(resultSet
                        .getInt("id_of_group"));
                subscription.setLastDay(resultSet
                        .getDate("last_day"));
                subscription.setLeftVisits(resultSet
                        .getInt("left_visits"));
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

    @Override
    public void deleteSubscription(Integer identity, Integer groupId)
            throws PersistentException {
        try (PreparedStatement statement
                     = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setInt(2, groupId);
            statement.setInt(1, identity);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public Integer create(Subscription subscription) throws PersistentException {
        ResultSet resultSet = null;
        try (PreparedStatement statement
                     = connection.prepareStatement(ADD_SUBSCRIPTION,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, subscription.getIdentity());
            statement.setInt(2, subscription.getIdOfGroup());
            statement.setInt(3, subscription.getLeftVisits());
            statement.setDate(4, subscription.getLastDay());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            return subscription.getIdentity();
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

    @Override
    public Subscription read(Integer identity) throws PersistentException {
        LOGGER.warn("Method is not supported for one subscription!");
        return null;
    }

    @Override
    public void update(Subscription subscription) throws PersistentException {
        try (PreparedStatement statement = connection
                .prepareStatement(UPDATE_SUBSCRIPTION)) {
            statement.setInt(1, subscription.getLeftVisits());
            statement.setDate(2, subscription.getLastDay());
            statement.setInt(3, subscription.getIdentity());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        LOGGER.warn("Method is not supported by only id!");
    }
}
