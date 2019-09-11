package by.popovich.last.dao.mysql;

import by.popovich.last.dao.UserDao;
import by.popovich.last.entity.Role;
import by.popovich.last.entity.User;
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
 * User DAO impl.
 */
public class UserDaoImpl extends BaseDaoImpl implements UserDao {
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
      .getLogger(UserDaoImpl.class);
    /**
     * Create sql.
     */
    private static final String CREATE_USER = "INSERT INTO `users` "
      + "(`login`, `password`, `role`) VALUES (?, ?, ?)";
    /**
     * Read by id sql.
     */
    private static final String READ_BY_ID = "SELECT *"
      + " FROM `users` WHERE `id` = ?";
    /**
     * Update sql.
     */
    private static final String UPDATE_USER_PARAMETERS = "UPDATE `users` SET "
      + "`login` = ?, `password` = ?, `role` = ? WHERE `id` = ?";
    /**
     * Delete sql.
     */
    private static final String DELETE_USER_BY_ID = "DELETE FROM "
      + "`users` WHERE `id` = ?";
    /**
     * By login and password sql.
     */
    private static final String USER_BY_LOGIN_AND_PASSWORD = "SELECT *"
      + " FROM `users` WHERE `login` = ? AND `password` = ?";

    /**
     * Read all sql.
     */
    private static final String READ_ALL_TABLE = "SELECT *"
      + " FROM `users` ORDER BY `login`";

    /**
     * Create.
     *
     * @param user user
     * @return id of user.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public Integer create(final User user) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(
              CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(three, user.getRole().getIdentity());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                LOGGER.info("User was created!");
                return resultSet.getInt(1);
            } else {
                LOGGER.error(
                  "There is no autoincremented index after trying to"
                    + ""
                    + " add record into table `users`");
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
     * @return user.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public User read(final Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_BY_ID);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setIdentity(identity);
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.getByIdentity(resultSet.getInt("role")));
            }
            return user;
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
     * @param user user.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public void update(final User user) throws PersistentException {
        try (PreparedStatement statement = connection
          .prepareStatement(UPDATE_USER_PARAMETERS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(three, user.getRole().getIdentity());
            statement.setInt(four, user.getIdentity());
            statement.executeUpdate();
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
               = connection.prepareStatement(DELETE_USER_BY_ID)) {
            statement.setInt(1, identity);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Read by login adn password.
     *
     * @param login    login.
     * @param password password.
     * @return user.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public User read(final String login,
                     final String password) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(USER_BY_LOGIN_AND_PASSWORD);
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setIdentity(resultSet.getInt("id"));
                user.setLogin(login);
                user.setPassword(password);
                user.setRole(Role.getByIdentity(resultSet
                  .getInt("role")));
            }
            return user;
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
     * @return list of users.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public List<User> readAll() throws PersistentException {
        try (PreparedStatement statement = connection
          .prepareStatement(READ_ALL_TABLE);
             ResultSet resultSet = statement.executeQuery()) {
            List<User> users = new ArrayList<>();
            User user = null;
            while (resultSet.next()) {
                user = new User();
                user.setIdentity(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.getByIdentity(
                  resultSet.getInt("role")));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }
}
