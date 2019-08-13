package by.popovich.last.service;

import by.popovich.last.dao.UserDao;
import by.popovich.last.dao.mysql.UserDaoImpl;
import by.popovich.last.dao.pool.ConnectionPool;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.List;

public class UserServiceImpl extends ServiceImpl implements UserService {
    @Override
    public List<User> readAll() throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserDao dao = new UserDaoImpl();
        ((UserDaoImpl) dao).setConnection(connection);
        List<User> users = dao.readAll();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User readByIdentity(Integer identity) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserDao dao = new UserDaoImpl();
        ((UserDaoImpl) dao).setConnection(connection);
        User user = ((UserDaoImpl) dao).read(identity);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User readByLoginAndPassword(String login, String password) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserDao dao = new UserDaoImpl();
        ((UserDaoImpl) dao).setConnection(connection);
        User user = dao.read(login, md5(password));
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void save(User user) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserDao dao = new UserDaoImpl();
        ((UserDaoImpl) dao).setConnection(connection);
        if(user.getIdentity() != null) {
            if(user.getPassword() != null) {
                user.setPassword(md5(user.getPassword()));
            } else {
                User oldUser = dao.read(user.getIdentity());
                user.setPassword(oldUser.getPassword());
            }
            dao.update(user);
        } else {
            if(user.getPassword() != null) {
                user.setPassword(md5(user.getPassword()));
            } else {
                user.setPassword(md5(new String()));
            }
            user.setIdentity(dao.create(user));
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserDao dao = new UserDaoImpl();
        ((UserDaoImpl) dao).setConnection(connection);
        dao.delete(identity);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String md5(String string) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("md5");
            digest.reset();
            digest.update(string.getBytes());
            byte hash[] = digest.digest();
            Formatter formatter = new Formatter();
            for(int i = 0; i < hash.length; i++) {
                formatter.format("%02X", hash[i]);
            }
            String md5summ = formatter.toString();
            formatter.close();
            return md5summ;
        } catch(NoSuchAlgorithmException e) {
            return null;
        }
    }
}
