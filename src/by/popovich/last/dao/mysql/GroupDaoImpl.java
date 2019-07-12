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

public class GroupDaoImpl extends BaseDaoImpl implements GroupDao {
    private static Logger LOGGER = LogManager.getLogger(ExerciseDaoImpl.class);

    private final String READ_TABLE = "SELECT * FROM `groups`";

    private final String READ_GROUP_BY_ID = "SELECT * "
            + "FROM groups WHERE group_id = ?";

    private final String READ_GROUP_BY_COACH_ID = "SELECT * "
            + "FROM groups WHERE coach_id = ?";

    private final String READ_GROUP_BY_TYPE = "SELECT * "
            + "FROM groups WHERE exercises_type = ?";

    private final String CREATE_GROUP = "INSERT INTO `groups` "
            + "(coach_id, exercises_type, max_clients, current_clients) "
            + "VALUES (?, ?, ?, 0)";

    private final String DELETE_BY_ID = "DELETE FROM "
            + "`groups` WHERE `group_id` = ?";

    private final String UPDATE_GROUP_PARAMETERS = "UPDATE `groups` SET "
            + "`coach_id` = ?, `exercises_type` = ?, `max_clients` = ?"
            + " WHERE `group_id` = ?";

    private final String UPDATE_CURRENT_CLIENTS = "UPDATE `groups` SET "
            + "`current_clients` = ? WHERE `group_id` = ?";

    @Override
    public void updateCurrent(int numberOfCurrent,
                              int group_id) throws PersistentException {
        try (PreparedStatement statement = connection
                .prepareStatement(UPDATE_CURRENT_CLIENTS)) {
            statement.setInt(1, numberOfCurrent);
            statement.setInt(2, group_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public void addVisitor(final int groupId) throws PersistentException {
        Group group = read(groupId);
        int updatedCurrent = group.getCurrentClients() + 1;
        updateCurrent(updatedCurrent, groupId);
    }

    @Override
    public void removeVisitor(final int groupId) throws PersistentException {
        Group group = read(groupId);
        int updatedCurrent = group.getCurrentClients() - 1;
        updateCurrent(updatedCurrent, groupId);
    }

    @Override
    public List<Group> readByCoachId(Integer identity) throws PersistentException {
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
        }    }

    @Override
    public List<Group> readGroupsByType(final int exercisesTypeId) throws PersistentException {
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
        }       }

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

    @Override
    public Integer create(Group group) throws PersistentException {
        ResultSet resultSet = null;
        try (PreparedStatement statement
                     = connection.prepareStatement(CREATE_GROUP,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, group.getCoachID());
            statement.setInt(2, group.getTypeOfExercisesId());
            statement.setInt(3, group.getMaxClients());
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

    @Override
    public Group read(Integer identity) throws PersistentException {
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

    @Override
    public void update(Group group) throws PersistentException {
        try {
            try (PreparedStatement statement = connection
                    .prepareStatement(UPDATE_GROUP_PARAMETERS)) {
                statement.setInt(1, group.getCoachID());
                statement.setInt(2, group.getTypeOfExercisesId());
                statement.setInt(3, group.getMaxClients());
                statement.setInt(4, group.getIdentity());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
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
