package by.popovich.last.dao.pool;

import by.popovich.last.exception.PersistentException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Connection pool realization.
 */
final public class ConnectionPool {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
      .getLogger(ConnectionPool.class);

    /**
     * URL.
     */
    private String url;
    /**
     * User.
     */
    private String user;
    /**
     * Password of user.
     */
    private String password;
    /**
     * Maximum size of pool.
     */
    private int maxSize;
    /**
     * Timeout.
     */
    private int checkConnectionTimeout;

    /**
     * Locker.
     */
    private ReentrantLock lock = new ReentrantLock();

    /**
     * Queue of free connections.
     */
    private BlockingQueue<PooledConnection> freeConnections
      = new LinkedBlockingQueue<>();
    /**
     * Set of used connections.
     */
    private Set<PooledConnection> usedConnections
      = new ConcurrentSkipListSet<>();

    /**
     * Default constructor.
     */
    private ConnectionPool() {
    }

    /**
     * Get connection.
     *
     * @return connection.
     * @throws PersistentException if error in DB handling.
     */
    public Connection getConnection() throws PersistentException {
        lock.lock();
        PooledConnection connection = null;
        while (connection == null) {
            try {
                if (!freeConnections.isEmpty()) {
                    connection = freeConnections.take();
                    if (!connection.isValid(checkConnectionTimeout)) {
                        try {
                            connection.getConnection().close();
                        } catch (SQLException e) {
                        }
                        connection = null;
                    }
                } else if (usedConnections.size() < maxSize) {
                    connection = createConnection();
                } else {
                    LOGGER.error("The limit of number of"
                      + " database connections is exceeded");
                    throw new PersistentException();
                }
            } catch (InterruptedException | SQLException e) {
                LOGGER.error("It is impossible to connect to a database", e);
                throw new PersistentException(e);
            }
        }
        usedConnections.add(connection);
        LOGGER.debug(String.format("Connection was received from pool."
            + " Current pool size: %d used connections; %d free connection",
          usedConnections.size(), freeConnections.size()));
        lock.unlock();
        return connection;
    }

    /**
     * Frees connection.
     *
     * @param connection taken connection.
     */
    void freeConnection(final PooledConnection connection) {
        lock.lock();
        try {
            if (connection.isValid(checkConnectionTimeout)) {
                connection.clearWarnings();
                connection.setAutoCommit(true);
                usedConnections.remove(connection);
                freeConnections.put(connection);
                LOGGER.debug(String.format("Connection was returned into pool."
                    + " Current pool size: %d used connections;"
                    + " %d free connection",
                  usedConnections.size(), freeConnections.size()));
            }
        } catch (SQLException | InterruptedException e1) {
            LOGGER.warn("It is impossible to return database"
              + " connection into pool", e1);
            try {
                connection.getConnection().close();
            } catch (SQLException e2) {
            }
        }
        lock.unlock();
    }

    /**
     * Init.
     *
     * @param driverClass                used driver.
     * @param urlInit                    URL.
     * @param userInit                   user login.
     * @param passwordInit               passowrd of user.
     * @param startSize                  size after init.
     * @param maxSizeInit                max size.
     * @param checkConnectionTimeoutInit timeout.
     * @throws PersistentException if error in DB handling.
     */
    public void init(final String driverClass, final String urlInit,
                     final String userInit, final String passwordInit,
                     final int startSize, final int maxSizeInit,
                     final int checkConnectionTimeoutInit)
      throws PersistentException {
        lock.lock();
        try {
            destroy();
            Class.forName(driverClass);
            this.url = urlInit;
            this.user = userInit;
            this.password = passwordInit;
            this.maxSize = maxSizeInit;
            this.checkConnectionTimeout = checkConnectionTimeoutInit;
            for (int counter = 0; counter < startSize; counter++) {
                freeConnections.put(createConnection());
            }
        } catch (ClassNotFoundException | SQLException
          | InterruptedException e) {
            LOGGER.fatal("It is impossible to initialize connection pool",
              e);
            throw new PersistentException(e);
        }
        lock.unlock();
    }

    /**
     * Singletone.
     */
    private static ConnectionPool instance = new ConnectionPool();

    /**
     * Getter for singletone.
     *
     * @return instance.
     */
    public static ConnectionPool getInstance() {
        return instance;
    }

    /**
     * Creates connection with help of driver.
     *
     * @return pooled connection.
     * @throws SQLException error with driver.
     */
    private PooledConnection createConnection() throws SQLException {
        return new PooledConnection(DriverManager.getConnection(url,
          user, password));
    }

    /**
     * Destroys connection pool, clears deque.
     */
    public void destroy() {
        lock.lock();
        usedConnections.addAll(freeConnections);
        freeConnections.clear();
        for (PooledConnection connection : usedConnections) {
            try {
                connection.getConnection().close();
            } catch (SQLException e) {
            }
        }
        usedConnections.clear();
        lock.unlock();
    }

    /**
     * Finalize.
     *
     * @throws Throwable some exception.
     */
    @Override
    protected void finalize() throws Throwable {
        destroy();
    }
}

