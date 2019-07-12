package by.popovich.last.dao.mysql;

import by.popovich.last.dao.UserInfoDao;
import by.popovich.last.entity.Person;
import by.popovich.last.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserInfoDaoImpl extends BaseDaoImpl implements UserInfoDao {
    private static Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    private final String ADD_USER_INFO = "INSERT INTO `user_info` "
            + "(`user_id`, `surname`, `name`, `patronymic`, `phone`)"
            + " VALUES (?, ?, ?, ?, ?)";

    private final String READ_BY_ID = "SELECT *"
            + " FROM `user_info` WHERE `user_id` = ?";

    private final String UPDATE_USER_PARAMETERS = "UPDATE `user_info` SET "
            + "`surname` = ?, `name` = ?, "
            + "  `patronymic` = ?,`phone` = ? WHERE `user_id` = ?";

    private final String DELETE_USER_BY_ID = "DELETE FROM "
            + "`user_info` WHERE `user_id` = ?";

    private final String READ_ALL_TABLE = "SELECT * FROM `user_info`";

    @Override
    public Integer create(Person person) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(ADD_USER_INFO,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, person.getIdentity());
            statement.setString(2, person.getSurname());
            statement.setString(3, person.getName());
            statement.setString(4, person.getPatronymic());
            statement.setLong(5, person.getPhone());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            return person.getIdentity();
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
    public Person read(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_BY_ID);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            Person person = null;
            if (resultSet.next()) {
                person = new Person();
                person.setIdentity(identity);
                person.setSurname(resultSet.getString("surname"));
                person.setName(resultSet.getString("name"));
                person.setPatronymic(resultSet.getString("patronymic"));
                person.setPhone(resultSet.getLong("phone"));
            }
            return person;
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
    public void update(Person person) throws PersistentException {
        try (PreparedStatement statement = connection
                .prepareStatement(UPDATE_USER_PARAMETERS)) {
            statement.setString(1, person.getSurname());
            statement.setString(2, person.getName());
            statement.setString(3, person.getPatronymic());
            statement.setLong(4, person.getPhone());
            statement.setInt(5, person.getIdentity());
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
    public List<Person> read() throws PersistentException {
        try (PreparedStatement statement = connection
                .prepareStatement(READ_ALL_TABLE);
             ResultSet resultSet = statement.executeQuery()) {
            List<Person> persons = new ArrayList<>();
            Person person = null;
            while (resultSet.next()) {
                person = new Person();
                person.setIdentity(resultSet.getInt("user_id"));
                person.setName(resultSet.getString("name"));
                person.setSurname(resultSet.getString("surname"));
                person.setPatronymic(resultSet.getString("patronymic"));
                person.setPhone(resultSet.getLong("phone"));
                persons.add(person);
            }
            return persons;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }
}
