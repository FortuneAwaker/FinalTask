package by.popovich.last.dao.mysql;

import by.popovich.last.dao.PriceDao;
import by.popovich.last.entity.Price;
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
 * Price DAO impl.
 */
public class PriceDaoImpl extends BaseDaoImpl implements PriceDao {
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
      .getLogger(ExerciseDaoImpl.class);

    /**
     * Read table sql.
     */
    private static final String READ_TABLE = "SELECT * FROM `prices`";
    /**
     * Read price by id sql.
     */
    private static final String READ_PRICE_BY_ID = "SELECT `exercises_type`,"
      + " `number_of_visits`, `number_of_days`, `price`"
      + " FROM prices WHERE id = ?";
    /**
     * Read by exercise type.
     */
    private static final String READ_PRICE_BY_EX_TYPE = "SELECT *"
      + " FROM prices WHERE exercises_type = ?";
    /**
     * Create price sql.
     */
    private static final String CREATE_PRICE = "INSERT INTO `prices` "
      + "(exercises_type, number_of_visits, number_of_days, price)"
      + " VALUES (?, ?, ?, ?)";
    /**
     * Delete by id sql.
     */
    private static final String DELETE_BY_ID = "DELETE FROM "
      + "`prices` WHERE `id` = ?";

    /**
     * Update price sql.
     */
    private static final String UPDATE_PRICE_PARAMETERS = "UPDATE `prices` SET "
      + "`exercises_type` = ?, `number_of_visits` = ?, "
      + "`number_of_days` = ?, `price` = ? WHERE `id` = ?";

    /**
     * Read by exercise type.
     *
     * @param type id.
     * @return list of prices.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public List<Price> readByExerciseType(final Integer type)
      throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(READ_PRICE_BY_EX_TYPE);
            statement.setInt(1, type);
            resultSet = statement.executeQuery();
            List<Price> prices = new ArrayList<>();
            Price price;
            while (resultSet.next()) {
                price = new Price();
                price.setIdentity(resultSet.getInt("id"));
                price.setTypeOfExercise(type);
                price.setNumberOfVisits(resultSet
                  .getInt("number_of_visits"));
                price.setPrice(resultSet.getDouble("price"));
                price.setNumberOfDays(resultSet
                  .getInt("number_of_days"));
                prices.add(price);
            }
            return prices;
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
     * @return list of prices.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public List<Price> read() throws PersistentException {
        try (PreparedStatement statement = connection
          .prepareStatement(READ_TABLE);
             ResultSet resultSet = statement.executeQuery()) {
            List<Price> prices = new ArrayList<>();
            Price price;
            while (resultSet.next()) {
                price = new Price();
                price.setIdentity(resultSet
                  .getInt("id"));
                price.setTypeOfExercise(resultSet
                  .getInt("exercises_type"));
                price.setNumberOfVisits(resultSet
                  .getInt("number_of_visits"));
                price.setNumberOfDays(resultSet
                  .getInt("number_of_days"));
                price.setPrice(resultSet.getDouble("price"));
                prices.add(price);
            }
            return prices;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Create price.
     *
     * @param price price.
     * @return id of price.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public Integer create(final Price price) throws PersistentException {
        ResultSet resultSet = null;
        try (PreparedStatement statement
               = connection.prepareStatement(CREATE_PRICE,
          Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, price.getTypeOfExercise());
            statement.setInt(2, price.getNumberOfVisits());
            statement.setInt(three, price.getNumberOfDays());
            statement.setDouble(four, price.getPrice());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                LOGGER.error("There is no autoincremented "
                  + "index after trying to"
                  + " add record into table `price`");
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
     *
     * @param identity id.
     * @return price.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public Price read(final Integer identity) throws PersistentException {
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection
          .prepareStatement(READ_PRICE_BY_ID)) {
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            Price price = null;
            if (resultSet.next()) {
                price = new Price();
                price.setIdentity(identity);
                price.setTypeOfExercise(resultSet
                  .getInt("exercises_type"));
                price.setNumberOfVisits(resultSet
                  .getInt("number_of_visits"));
                price.setNumberOfDays(resultSet
                  .getInt("number_of_days"));
                price.setPrice(resultSet.getDouble("price"));
            }
            return price;
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
     * Update price.
     *
     * @param price price.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public void update(final Price price) throws PersistentException {
        try (PreparedStatement statement = connection
          .prepareStatement(UPDATE_PRICE_PARAMETERS)) {
            statement.setInt(1, price.getTypeOfExercise());
            statement.setInt(2, price.getNumberOfVisits());
            statement.setInt(three, price.getNumberOfDays());
            statement.setDouble(four, price.getPrice());
            statement.setInt(five, price.getIdentity());
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
               = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, identity);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }
}
