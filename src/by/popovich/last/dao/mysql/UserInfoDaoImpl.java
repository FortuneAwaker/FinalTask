package by.popovich.last.dao.mysql;

import by.popovich.last.dao.UserInfoDao;
import by.popovich.last.entity.Person;
import by.popovich.last.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * User info DAO.
 */
public class UserInfoDaoImpl extends BaseDaoImpl implements UserInfoDao {
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
     * Six.
     */
    private final int six = 6;
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
      .getLogger(UserDaoImpl.class);
    /**
     * Add sql.
     */
    private static final String ADD_USER_INFO = "INSERT INTO `user_info` "
      + "(`user_id`, `surname`, `name`, `patronymic`, `phone`)"
      + " VALUES (?, ?, ?, ?, ?)";
    /**
     * Read by id sql.
     */
    private static final String READ_BY_ID = "SELECT *"
      + " FROM `user_info` WHERE `user_id` = ?";
    /**
     * Update sql.
     */
    private static final String UPDATE_USER_PARAMETERS
      = "UPDATE `user_info` SET "
      + "`surname` = ?, `name` = ?, "
      + "  `patronymic` = ?,`phone` = ?,`avatar` = ? WHERE `user_id` = ?";

    /**
     * Delete by id sq.
     */
    private static final String DELETE_USER_BY_ID = "DELETE FROM "
      + "`user_info` WHERE `user_id` = ?";

    /**
     * Read all sql.
     */
    private static final String READ_ALL_TABLE = "SELECT * FROM `user_info`";
    /**
     * Read image sql.
     */
    private static final String READ_IMAGE_BY_USER_ID
      = "SELECT `avatar` FROM `user_info`"
      + " WHERE `user_id` = ?";

    /**
     * Create.
     *
     * @param person user info.
     * @return id of user.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public Integer create(final Person person) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(ADD_USER_INFO,
              Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, person.getIdentity());
            statement.setString(2, person.getSurname());
            statement.setString(three, person.getName());
            statement.setString(four, person.getPatronymic());
            statement.setLong(five, person.getPhone());
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

    /**
     * Read by id.
     *
     * @param identity id.
     * @return user info.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public Person read(final Integer identity) throws PersistentException {
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

    /**
     * Update, is not supported.
     *
     * @param entity instance.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public void update(final Person entity) throws PersistentException {

    }

    /**
     * Update.
     *
     * @param person user info.
     * @param is     image.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public void update(final Person person,
                       final InputStream is) throws PersistentException {
        try (PreparedStatement statement = connection
          .prepareStatement(UPDATE_USER_PARAMETERS)) {
            statement.setString(1, person.getSurname());
            statement.setString(2, person.getName());
            statement.setString(three, person.getPatronymic());
            statement.setLong(four, person.getPhone());
            statement.setObject(five, is);
            statement.setInt(six, person.getIdentity());
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
     * Read all.
     *
     * @return list of user info.
     * @throws PersistentException if error in DB handling.
     */
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

    /**
     * Read image.
     *
     * @param userId id of user.
     * @return byte array.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public byte[] readImage(final Integer userId) throws PersistentException {
        try (PreparedStatement statement
               = connection.prepareStatement(READ_IMAGE_BY_USER_ID)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                byte[] byteArray = resultSet.getBytes(1);
                return byteArray;
            }
            return null;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }
}
