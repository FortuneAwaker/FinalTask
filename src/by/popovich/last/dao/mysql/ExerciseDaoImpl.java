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

/**
 * Exercise DAO impl.
 */
public class ExerciseDaoImpl extends BaseDaoImpl implements ExerciseDao {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
      .getLogger(ExerciseDaoImpl.class);

    /**
     * Read table sql.
     */
    private static final String READ_TABLE = "SELECT `exercises_id`, "
      + "`exercises_type` FROM exercises";

    /**
     * Read exercise by id sql.
     */
    private static final String READ_EXERCISE_BY_ID = "SELECT `exercises_type` "
      + "FROM exercises WHERE exercises_id = ?";

    /**
     * Update exercise sql.
     */
    private static final String UPDATE_EXERCISE = "UPDATE `exercises` SET "
      + "`exercises_type` = ?"
      + " WHERE `exercises_id` = ?";

    /**
     * Create exercise sql.
     */
    private static final String CREATE_EXERCISE = "INSERT INTO `exercises`"
      + " (exercises_type) VALUES (?)";

    /**
     * Delete exercise sql.
     */
    private static final String DELETE_BY_ID = "DELETE FROM "
      + "`exercises` WHERE `exercises_id` = ?";

    /**
     * Read by name sql.
     */
    private static final String READ_ID_BY_NAME = "SELECT `exercises_id` FROM "
      + "`exercises` WHERE `exercises_type` = ?";

    /**
     * Read all table.
     * @return list of exercises.
     * @throws PersistentException if error in DB handling.
     */
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

    /**
     * Read exercise by name.
     * @param name name of exercise.
     * @return exercise.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public Exercise readIdByName(final String name) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_ID_BY_NAME);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            Exercise exercise = null;
            if (resultSet.next()) {
                exercise = new Exercise();
                exercise.setIdentity(resultSet.getInt(
                  "exercises_id"));
                exercise.setTypeOfExercises(name);
            }
            return exercise;
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
     * Add record.
     * @param exercise new exercise.
     * @return id of exercise.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public Integer create(final Exercise exercise) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(CREATE_EXERCISE,
              Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, exercise.getTypeOfExercises());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                LOGGER.error("There is no autoincremented "
                  + "index after trying to"
                  + " add record into table `exercises`");
                throw new PersistentException();
            }
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

    /**
     * Read by id.
     * @param identity id.
     * @return exercise.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public Exercise read(final Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_EXERCISE_BY_ID);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            Exercise exercise = null;
            if (resultSet.next()) {
                exercise = new Exercise();
                exercise.setIdentity(identity);
                exercise.setTypeOfExercises(resultSet
                  .getString("exercises_type"));
            }
            return exercise;
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
     * Update exercise.
     * @param exercise exercise.
     * @throws PersistentException if error in DB handling.
     */
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
        }
    }

    /**
     * Delete exercise.
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
