package by.popovich.last.dao.mysql;

import by.popovich.last.dao.*;
import by.popovich.last.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionImpl implements Transaction {
    private static Logger LOGGER = LogManager.getLogger(TransactionImpl.class);

    private Connection connection;

    public TransactionImpl(Connection connection) {
        this.connection = connection;
    }

    private static Map<Class<? extends DAO<?>>,
            Class<? extends BaseDaoImpl>> classes = new ConcurrentHashMap<>();

    static {
        classes.put(ExerciseDao.class, ExerciseDaoImpl.class);
        classes.put(UserDao.class, UserDaoImpl.class);
        classes.put(GroupDao.class, GroupDaoImpl.class);
        classes.put(PriceDao.class, PriceDaoImpl.class);
        classes.put(SubscriptionDao.class, SubscriptionDaoImpl.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <Type extends DAO<?>> Type createDao(Class<Type> key)
            throws PersistentException {
        Class<? extends BaseDaoImpl> value = classes.get(key);
        if(value != null) {
            try {
                BaseDaoImpl dao = value.newInstance();
                dao.setConnection(connection);
                return (Type)dao;
            } catch(InstantiationException | IllegalAccessException e) {
                LOGGER.error(
                        "It is impossible to create data access object", e);
                throw new PersistentException(e);
            }
        }
        return null;
    }

    @Override
    public void commit() throws PersistentException {
        try {
            connection.commit();
        } catch(SQLException e) {
            LOGGER.error("It is impossible to commit transaction", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void rollback() throws PersistentException {
        try {
            connection.rollback();
        } catch(SQLException e) {
            LOGGER.error("It is impossible to rollback transaction", e);
            throw new PersistentException(e);
        }
    }

}
