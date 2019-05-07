package by.popovich.last.dao.mysql;

import by.popovich.last.dao.QueueDao;
import by.popovich.last.entity.Queue;
import by.popovich.last.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueueDaoImpl extends BaseDaoImpl implements QueueDao {
    private static Logger LOGGER = LogManager.getLogger(ExerciseDaoImpl.class);

    private final String READ_TABLE = "SELECT * FROM `queue`";

    private final String READ_QUEUE_BY_ID = "SELECT `wanted_type`"
            + " FROM queue WHERE waiter_id = ?";

    private final String READ_QUEUE_BY_TYPE = "SELECT `waiter_id`, "
            + "`wanted_type` FROM queue WHERE wanted_type = ?";

    private final String CREATE_QUEUE = "INSERT INTO `queue` "
            + "(waiter_id, wanted_type) VALUES (?, ?)";

    private final String DELETE_BY_ID = "DELETE FROM "
            + "`queue` WHERE `waiter_id` = ?";


    @Override
    public List<Queue> read() throws PersistentException {
        try (PreparedStatement statement = connection
                .prepareStatement(READ_TABLE);
             ResultSet resultSet = statement.executeQuery()) {
            List<Queue> queues = new ArrayList<>();
            Queue queue;
            while (resultSet.next()) {
                queue = new Queue();
                queue.setIdentity(resultSet
                        .getInt("waiter_id"));
                queue.setWantedType(resultSet.getInt("wanted_type"));
                queues.add(queue);
            }
            return queues;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Queue> readByWantedType(Integer wantedType) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_QUEUE_BY_TYPE);
            statement.setInt(1, wantedType);
            resultSet = statement.executeQuery();
            List<Queue> queues = new ArrayList<>();
            Queue queue;
            while (resultSet.next()) {
                queue = new Queue();
                queue.setIdentity(resultSet.getInt("waiter_id"));
                queue.setWantedType(wantedType);
                queues.add(queue);
            }
            return queues;
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
    public Integer create(Queue queue) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(CREATE_QUEUE,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, queue.getIdentity());
            statement.setInt(2, queue.getWantedType());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            return queue.getIdentity();
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public Queue read(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_QUEUE_BY_ID);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            Queue queue = null;
            if (resultSet.next()) {
                queue = new Queue();
                queue.setIdentity(identity);
                queue.setWantedType(resultSet.getInt("wanted_type"));
            }
            return queue;
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
    public void update(Queue entity) throws PersistentException {
        LOGGER.warn("Method is not supported!");
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        try (PreparedStatement statement
                     = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, identity);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }
}
