package by.popovich.last.dao.mysql;

import by.popovich.last.dao.ExerciseDao;
import by.popovich.last.entity.Exercise;
import by.popovich.last.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDaoImpl extends BaseDaoImpl implements ExerciseDao {
    private static Logger LOGGER = LogManager.getLogger(ExerciseDaoImpl.class);

    private final String READ_TABLE = "SELECT `exercises_id`, "
            + "`exercises_type` FROM exercises";

    private final String READ_EXERCISE_BY_ID = "SELECT `exercises_type` "
            + "FROM exercises WHERE exercises_id = ?";

    private final String UPDATE_EXERCISE = "UPDATE `exercises` SET "
            + "`exercises_type` = ?"
            + " WHERE `exercises_id` = ?";

    private final String CREATE_EXERCISE = "INSERT INTO `exercises`"
            + " (exercises_type) VALUES (?)";

    private final String DELETE_BY_ID = "DELETE FROM "
            + "`exercises` WHERE `exercises_id` = ?";

    private final String READ_ID_BY_NAME = "SELECT `exercises_id` FROM "
            + "`exercises` WHERE `exercises_type` = ?";

    @Override
    public List<Exercise> read() throws PersistentException {
        try (PreparedStatement statement = connection
                .prepareStatement(READ_TABLE);
             ResultSet resultSet = statement.executeQuery()) {
            List<Exercise> exercises = new ArrayList<>();
            Exercise exercise;
            while (resultSet.next()) {
                exercise = new Exercise();
                exercise.setIdentity(resultSet
                        .getInt("exercises_id"));
                exercise.setTypeOfExercises(resultSet
                        .getString("exercises_type"));
                exercises.add(exercise);
            }
            return exercises;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public Exercise readIdByName(String name) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_ID_BY_NAME);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            Exercise exercise = null;
            if(resultSet.next()) {
                exercise = new Exercise();
                exercise.setIdentity(resultSet.getInt("exercises_id"));
                exercise.setTypeOfExercises(name);
            }
            return exercise;
        } catch(SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch(SQLException | NullPointerException ignored) {}
            try {
                statement.close();
            } catch(SQLException | NullPointerException ignored) {}
        }
    }

    @Override
    public Integer create(Exercise exercise) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(CREATE_EXERCISE,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, exercise.getTypeOfExercises());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                LOGGER.error("There is no autoincremented "
                        + "index after trying to"
                        + " add record into table `exercises`");
                throw new PersistentException();
            }
        } catch(SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch(SQLException | NullPointerException e) {}
            try {
                statement.close();
            } catch(SQLException | NullPointerException e) {}
        }
    }

    @Override
    public Exercise read(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_EXERCISE_BY_ID);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            Exercise exercise = null;
            if(resultSet.next()) {
                exercise = new Exercise();
                exercise.setIdentity(identity);
                exercise.setTypeOfExercises(resultSet
                        .getString("exercises_type"));
            }
            return exercise;
        } catch(SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch(SQLException | NullPointerException ignored) {}
            try {
                statement.close();
            } catch(SQLException | NullPointerException ignored) {}
        }
    }

    @Override
    public void update(final Exercise exercise) throws PersistentException {
        try {
            try (PreparedStatement statement = connection
                    .prepareStatement(UPDATE_EXERCISE)) {
                statement.setInt(2, exercise.getIdentity());
                statement.setString(
                        1, exercise.getTypeOfExercises());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new PersistentException(e);
        }    }

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
