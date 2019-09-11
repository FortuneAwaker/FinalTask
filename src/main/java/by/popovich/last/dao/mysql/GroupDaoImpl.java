package by.popovich.last.dao.mysql;

import by.popovich.last.dao.GroupDao;
import by.popovich.last.entity.Group;
import by.popovich.last.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Group DAO impl.
 */
public class GroupDaoImpl extends BaseDaoImpl implements GroupDao {
    /**
     * Three.
     */
    private final int three = 3;
    /**
     * Four.
     */
    private final int four = 4;
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
      .getLogger(ExerciseDaoImpl.class);

    /**
     * Read table sql.
     */
    private static final String READ_TABLE = "SELECT * FROM `groups`";

    /**
     * Read group by id sql.
     */
    private static final String READ_GROUP_BY_ID = "SELECT * "
      + "FROM groups WHERE group_id = ?";

    /**
     * Read group by coach id sql.
     */
    private static final String READ_GROUP_BY_COACH_ID = "SELECT * "
      + "FROM groups WHERE coach_id = ?";
    /**
     * Read group by type of exercises sql.
     */
    private static final String READ_GROUP_BY_TYPE = "SELECT * "
      + "FROM groups WHERE exercises_type = ?";
    /**
     * Create group sql.
     */
    private static final String CREATE_GROUP = "INSERT INTO `groups` "
      + "(coach_id, exercises_type, max_clients, current_clients) "
      + "VALUES (?, ?, ?, 0)";
    /**
     * Delete by id sql.
     */
    private static final String DELETE_BY_ID = "DELETE FROM "
      + "`groups` WHERE `group_id` = ?";
    /**
     * Update sql.
     */
    private static final String UPDATE_GROUP_PARAMETERS = "UPDATE `groups` SET "
      + "`coach_id` = ?, `exercises_type` = ?, `max_clients` = ?"
      + " WHERE `group_id` = ?";
    /**
     * Update current sql.
     */
    private static final String UPDATE_CURRENT_CLIENTS = "UPDATE `groups` SET "
      + "`current_clients` = ? WHERE `group_id` = ?";

    /**
     * Update current visitors.
     *
     * @param numberOfCurrent new number.
     * @param groupId        id.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public void updateCurrent(final int numberOfCurrent,
                              final int groupId) throws PersistentException {
        try (PreparedStatement statement = connection
          .prepareStatement(UPDATE_CURRENT_CLIENTS)) {
            statement.setInt(1, numberOfCurrent);
            statement.setInt(2, groupId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Add visitor.
     *
     * @param groupId id of group.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public void addVisitor(final int groupId) throws PersistentException {
        Group group = read(groupId);
        int updatedCurrent = group.getCurrentClients() + 1;
        updateCurrent(updatedCurrent, groupId);
    }

    /**
     * Remove visitor.
     *
     * @param groupId id of group.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public void removeVisitor(final int groupId) throws PersistentException {
        Group group = read(groupId);
        int updatedCurrent = group.getCurrentClients() - 1;
        updateCurrent(updatedCurrent, groupId);
    }

    /**
     * Read by coach id.
     *
     * @param identity id of coach.
     * @return list of group.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public List<Group> readByCoachId(final Integer identity)
      throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_GROUP_BY_COACH_ID);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            Group group;
            ArrayList<Group> groups = new ArrayList<>();
            while (resultSet.next()) {
                group = new Group();
                group.setIdentity(resultSet.getInt("group_id"));
                group.setTypeOfExercisesId(resultSet
                  .getInt("exercises_type"));
                group.setCoachID(identity);
                group.setMaxClients(resultSet.getInt("max_clients"));
                group.setCurrentClients(resultSet.getInt(
                  "current_clients"
                ));
                groups.add(group);
            }
            return groups;
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
     * Read by type of exercises.
     * @param exercisesTypeId id of exercise.
     * @return list of group.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public List<Group> readGroupsByType(final int exercisesTypeId)
      throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_GROUP_BY_TYPE);
            statement.setInt(1, exercisesTypeId);
            resultSet = statement.executeQuery();
            Group group;
            ArrayList<Group> groups = new ArrayList<>();
            while (resultSet.next()) {
                group = new Group();
                group.setIdentity(resultSet.getInt("group_id"));
                group.setTypeOfExercisesId(resultSet
                  .getInt("exercises_type"));
                group.setCoachID(resultSet
                  .getInt("coach_id"));
                group.setMaxClients(resultSet.getInt("max_clients"));
                group.setCurrentClients(resultSet.getInt(
                  "current_clients"
                ));
                groups.add(group);
            }
            return groups;
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
     * Read all table.
     * @return list of group.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public List<Group> read() throws PersistentException {
        try (PreparedStatement statement = connection
          .prepareStatement(READ_TABLE);
             ResultSet resultSet = statement.executeQuery()) {
            List<Group> groups = new ArrayList<>();
            Group group;
            while (resultSet.next()) {
                group = new Group();
                group.setIdentity(resultSet
                  .getInt("group_id"));
                group.setCoachID(resultSet.getInt("coach_id"));
                group.setTypeOfExercisesId(resultSet
                  .getInt("exercises_type"));
                group.setMaxClients(resultSet.getInt("max_clients"));
                group.setCurrentClients(resultSet.getInt(
                  "current_clients"
                ));
                groups.add(group);
            }
            return groups;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Create group.
     * @param group new group.
     * @return id of group.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public Integer create(final Group group) throws PersistentException {
        ResultSet resultSet = null;
        try (PreparedStatement statement
               = connection.prepareStatement(CREATE_GROUP,
          Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, group.getCoachID());
            statement.setInt(2, group.getTypeOfExercisesId());
            statement.setInt(three, group.getMaxClients());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                LOGGER.error("There is no autoincremented "
                  + "index after trying to"
                  + " add record into table `groups`");
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
     * @param identity id.
     * @return group.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public Group read(final Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_GROUP_BY_ID);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            Group group = null;
            if (resultSet.next()) {
                group = new Group();
                group.setIdentity(identity);
                group.setMaxClients(resultSet.getInt("max_clients"));
                group.setCurrentClients(resultSet.getInt(
                  "current_clients"
                ));
                group.setTypeOfExercisesId(resultSet
                  .getInt("exercises_type"));
                group.setCoachID(resultSet.getInt("coach_id"));
            }
            return group;
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
     * @param group group.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public void update(final Group group) throws PersistentException {
        try {
            try (PreparedStatement statement = connection
              .prepareStatement(UPDATE_GROUP_PARAMETERS)) {
                statement.setInt(1, group.getCoachID());
                statement.setInt(2, group.getTypeOfExercisesId());
                statement.setInt(three, group.getMaxClients());
                statement.setInt(four, group.getIdentity());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Delete group.
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
