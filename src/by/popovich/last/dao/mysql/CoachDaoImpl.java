package by.popovich.last.dao.mysql;

import by.popovich.last.dao.CoachDao;
import by.popovich.last.entity.Coach;
import by.popovich.last.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CoachDaoImpl extends BaseDaoImpl implements CoachDao {
    private static Logger LOGGER = LogManager.getLogger(ExerciseDaoImpl.class);

    private final String READ_TABLE = "SELECT * FROM `coach`";

    private final String READ_COACH_BY_ID = "SELECT `id`, `max_clients`, "
            + "`current_clients`, `salary` FROM coach WHERE id = ?";

    private final String READ_COACH_BY_SALARY = "SELECT `id`, `max_clients`, "
            + "`current_clients`, `salary` FROM coach WHERE salary = ?";

    private final String ADD_COACH = "INSERT INTO `coach` "
            + "(id, max_clients, current_clients, salary) VALUES (?, ?, ?, ?)";

    private final String DELETE_BY_ID = "DELETE FROM "
            + "`coach` WHERE `id` = ?";

    private final String UPDATE_COACH_PARAMETERS = "UPDATE `coach` SET "
            + "`max_clients` = ?, `current_clients` = ?, `salary` = ?"
            + " WHERE `id` = ?";

    private final String UPDATE_CURRENT_CLIENTS = "UPDATE `coach` SET "
            + "`current_clients` = ? WHERE `id` = ?";

    @Override
    public void updateCurrent(int numberOfCurrent,
                              int coachId) throws PersistentException {
        try (PreparedStatement statement = connection
                .prepareStatement(UPDATE_CURRENT_CLIENTS)) {
            statement.setInt(1, numberOfCurrent);
            statement.setInt(2, coachId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Coach> read() throws PersistentException {
        try (PreparedStatement statement = connection
                .prepareStatement(READ_TABLE);
             ResultSet resultSet = statement.executeQuery()) {
            List<Coach> coachs = new ArrayList<>();
            Coach coach;
            while (resultSet.next()) {
                coach = new Coach();
                coach.setIdentity(resultSet
                        .getInt("id"));
                coach.setMaxClients(resultSet.getInt("max_clients"));
                coach.setCurrentClients(resultSet
                        .getInt("current_clients"));
                coach.setSalary(resultSet.getDouble("salary"));
                coachs.add(coach);
            }
            return coachs;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Coach> readBySalary(double salary) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_COACH_BY_SALARY);
            statement.setDouble(1, salary);
            resultSet = statement.executeQuery();
            Coach coach;
            ArrayList<Coach> coachs = new ArrayList<>();
            while (resultSet.next()) {
                coach = new Coach();
                coach.setIdentity(coach.getIdentity());
                coach.setMaxClients(resultSet
                        .getInt("max_clients"));
                coach.setCurrentClients(resultSet
                        .getInt("current_clients"));
                coach.setSalary(salary);
                coachs.add(coach);
            }
            return coachs;
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
    public Integer create(Coach coach) throws PersistentException {
        ResultSet resultSet = null;
        try (PreparedStatement statement
                     = connection.prepareStatement(ADD_COACH,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, coach.getIdentity());
            statement.setInt(2, coach.getMaxClients());
            statement.setInt(3, coach.getCurrentClients());
            statement.setDouble(4, coach.getSalary());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            return coach.getIdentity();
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
    public Coach read(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_COACH_BY_ID);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            Coach coach = null;
            if (resultSet.next()) {
                coach = new Coach();
                coach.setIdentity(identity);
                coach.setMaxClients(resultSet
                        .getInt("max_clients"));
                coach.setSalary(resultSet.getDouble("salary"));
                coach.setCurrentClients(resultSet
                        .getInt("current_clients"));
            }
            return coach;
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
    public void update(Coach coach) throws PersistentException {
        try (PreparedStatement statement = connection
                .prepareStatement(UPDATE_COACH_PARAMETERS)) {
            statement.setInt(1, coach.getMaxClients());
            statement.setInt(2, coach.getCurrentClients());
            statement.setDouble(3, coach.getSalary());
            statement.setInt(4, coach.getIdentity());
            statement.executeUpdate();
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
