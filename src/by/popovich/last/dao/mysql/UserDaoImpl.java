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

public class UserDaoImpl extends BaseDaoImpl implements UserDao {
    private static Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    private final String CREATE_USER = "INSERT INTO `users` "
            + "(`login`, `password`, `role`) VALUES (?, ?, ?)";

    private final String READ_BY_ID = "SELECT *"
            + " FROM `users` WHERE `id` = ?";

    private final String UPDATE_USER_PARAMETERS = "UPDATE `users` SET "
            + "`login` = ?, `password` = ?, `role` = ? WHERE `id` = ?";

    private final String DELETE_USER_BY_ID = "DELETE FROM "
            + "`users` WHERE `id` = ?";

    private final String USER_BY_LOGIN_AND_PASSWORD = "SELECT *"
            + " FROM `users` WHERE `login` = ? AND `password` = ?";

    private final String READ_ALL_TABLE = "SELECT *"
            + " FROM `users` ORDER BY `login`";

    @Override
    public Integer create(User user) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().getIdentity());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                LOGGER.info("User was created!");
                return resultSet.getInt(1);
            } else {
                LOGGER.error("There is no autoincremented index after trying to" +
                        "" +
                        " add record into table `users`");
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
    public User read(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_BY_ID);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            User user = null;
            if(resultSet.next()) {
                user = new User();
                user.setIdentity(identity);
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.getByIdentity(resultSet.getInt("role")));
            }
            return user;
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
    public void update(User user) throws PersistentException {
        try (PreparedStatement statement = connection
                .prepareStatement(UPDATE_USER_PARAMETERS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().getIdentity());
            statement.setInt(4, user.getIdentity());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        try (PreparedStatement statement
                     = connection.prepareStatement(DELETE_USER_BY_ID)) {
            statement.setInt(1, identity);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public User read(String login, String password) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(USER_BY_LOGIN_AND_PASSWORD);
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            User user = null;
            if(resultSet.next()) {
                user = new User();
                user.setIdentity(resultSet.getInt("id"));
                user.setLogin(login);
                user.setPassword(password);
                user.setRole(Role.getByIdentity(resultSet
                        .getInt("role")));
            }
            return user;
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
