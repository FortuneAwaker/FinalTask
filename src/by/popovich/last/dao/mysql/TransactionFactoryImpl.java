package by.popovich.last.dao.mysql;

import by.popovich.last.dao.Transaction;
import by.popovich.last.dao.TransactionFactory;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.dao.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Transaction factory impl.
 */
public class TransactionFactoryImpl implements TransactionFactory {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
      .getLogger(TransactionFactoryImpl.class);
    /**
     * Connection.
     */
    private Connection connection;

    /**
     * Default constructor.
     *
     * @throws PersistentException if error in DB handling.
     */
    public TransactionFactoryImpl() throws PersistentException {
        connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("It is impossible to turn off "
              + "autocommiting for database connection", e);
            throw new PersistentException(e);
        }
    }

    /**
     * Create transaction.
     *
     * @return transaction.
     * @throws PersistentException if error in DB handling.
     */
    @Override
    public Transaction createTransaction() throws PersistentException {
        return new TransactionImpl(connection);
    }

    /**
     * Closas.
     */
    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
        }
    }
}
