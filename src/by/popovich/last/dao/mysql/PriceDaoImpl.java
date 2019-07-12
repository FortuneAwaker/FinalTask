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

public class PriceDaoImpl extends BaseDaoImpl implements PriceDao {
    private static Logger LOGGER = LogManager.getLogger(ExerciseDaoImpl.class);

    private final String READ_TABLE = "SELECT * FROM `prices`";

    private final String READ_PRICE_BY_ID = "SELECT `exercises_type`,"
            + " `number_of_visits`, `number_of_days`, `price`"
            + " FROM prices WHERE id = ?";

    private final String READ_PRICE_BY_EX_TYPE = "SELECT *"
            + " FROM prices WHERE exercises_type = ?";

    private final String CREATE_PRICE = "INSERT INTO `prices` "
            + "(exercises_type, number_of_visits, number_of_days, price)"
            + " VALUES (?, ?, ?, ?)";

    private final String DELETE_BY_ID = "DELETE FROM "
            + "`prices` WHERE `id` = ?";

    private final String UPDATE_PRICE_PARAMETERS = "UPDATE `prices` SET "
            + "`exercises_type` = ?, `number_of_visits` = ?, "
            + "`number_of_days` = ?, `price` = ? WHERE `id` = ?";

    @Override
    public List<Price> readByExerciseType(Integer type) throws PersistentException {
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
        }    }

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

    @Override
    public Integer create(Price price) throws PersistentException {
        ResultSet resultSet = null;
        try (PreparedStatement statement
                     = connection.prepareStatement(CREATE_PRICE,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, price.getTypeOfExercise());
            statement.setInt(2, price.getNumberOfVisits());
            statement.setInt(3, price.getNumberOfDays());
            statement.setDouble(4, price.getPrice());
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

    @Override
    public Price read(Integer identity) throws PersistentException {
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

    @Override
    public void update(Price price) throws PersistentException {
        try (PreparedStatement statement = connection
                .prepareStatement(UPDATE_PRICE_PARAMETERS)) {
            statement.setInt(1, price.getTypeOfExercise());
            statement.setInt(2, price.getNumberOfVisits());
            statement.setInt(3, price.getNumberOfDays());
            statement.setDouble(4, price.getPrice());
            statement.setInt(5, price.getIdentity());
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
