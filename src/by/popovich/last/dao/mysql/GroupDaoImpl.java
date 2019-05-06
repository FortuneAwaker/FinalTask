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

    private final String READ_GROUP_BY_ID = "SELECT `coach_id`, "
            + "`exercises_type` FROM groups WHERE group_id = ?";

    private final String READ_GROUP_BY_COACH_ID = "SELECT `group_id`, "
            + "`exercises_type` FROM groups WHERE coach_id = ?";

    private final String CREATE_GROUP = "INSERT INTO `groups` "
            + "(coach_id, exercises_type) VALUES (?, ?)";

    private final String DELETE_BY_ID = "DELETE FROM "
            + "`groups` WHERE `group_id` = ?";

    private final String UPDATE_GROUP_PARAMETERS = "UPDATE `groups` SET "
            + "`coach_id` = ?, `exercises_type` = ? WHERE `group_id` = ?";

    @Override
    public Group readByCoachId(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_GROUP_BY_COACH_ID);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            Group group = null;
            if (resultSet.next()) {
                group = new Group();
                group.setIdentity(resultSet.getInt("group_id"));
                group.setTypeOfExercisesId(resultSet
                        .getInt("exercise_type"));
                group.setCoachID(identity);
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
        }    }

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
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                LOGGER.error("There is no autoincremented "
                        + "index after trying to"
                        + " add record into table `users`");
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
                group.setTypeOfExercisesId(resultSet
                        .getInt("exercise_type"));
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
                statement.setInt(3, group.getIdentity());
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
