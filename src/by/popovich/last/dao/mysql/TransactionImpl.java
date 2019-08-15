package by.popovich.last.dao.mysql;

import by.popovich.last.dao.DAO;
import by.popovich.last.dao.ExerciseDao;
import by.popovich.last.dao.GroupDao;
import by.popovich.last.dao.PriceDao;
import by.popovich.last.dao.SubscriptionDao;
import by.popovich.last.dao.Transaction;
import by.popovich.last.dao.UserDao;
import by.popovich.last.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Transaction impl.
 */
public class TransactionImpl implements Transaction {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
      .getLogger(TransactionImpl.class);
    /**
     * Connection.
     */
    private Connection connection;

    /**
     * Constructor.
     *
     * @param con connection.
     */
    public TransactionImpl(final Connection con) {
        this.connection = con;
    }

    /**
     * Map of DAO interfaces and impls.
     */
    private static Map<Class<? extends DAO<?>>,
      Class<? extends BaseDaoImpl>> classes = new ConcurrentHashMap<>();

    /**
     * Putting classes.
     */
    static {
        classes.put(ExerciseDao.class, ExerciseDaoImpl.class);
        classes.put(UserDao.class, UserDaoImpl.class);
        classes.put(GroupDao.class, GroupDaoImpl.class);
        classes.put(PriceDao.class, PriceDaoImpl.class);
        classes.put(SubscriptionDao.class, SubscriptionDaoImpl.class);
    }

    /**
     * Creates DAO with connection.
     *
     * @param key    entity class.
     * @param <Type> entity.
     * @return DAO class with connection.
     * @throws PersistentException if error in DB handling.
     */
    @SuppressWarnings("unchecked")
    @Override
    public <Type extends DAO<?>> Type createDao(final Class<Type> key)
      throws PersistentException {
        Class<? extends BaseDaoImpl> value = classes.get(key);
        if (value != null) {
            try {
                BaseDaoImpl dao = value.newInstance();
                dao.setConnection(connection);
                return (Type) dao;
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.error(
                  "It is impossible to create data access object", e);
                throw new PersistentException(e);
            }
        }
        return null;
    }

    /**
     * Commit.
     *
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public void commit() throws PersistentException {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("It is impossible to commit transaction", e);
            throw new PersistentException(e);
        }
    }

    /**
     * Rollback.
     *
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public void rollback() throws PersistentException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("It is impossible to rollback transaction", e);
            throw new PersistentException(e);
        }
    }

}
