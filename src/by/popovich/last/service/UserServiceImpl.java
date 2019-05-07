package by.popovich.last.service;

import by.popovich.last.dao.UserDao;
import by.popovich.last.entity.User;
import by.popovich.last.exception.PersistentException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.List;

public class UserServiceImpl extends ServiceImpl implements UserService {
    @Override
    public List<User> findAll() throws PersistentException {
        UserDao dao = transaction.createDao(UserDao.class);
        return dao.read();
    }

    @Override
    public User findByIdentity(Integer identity) throws PersistentException {
        UserDao dao = transaction.createDao(UserDao.class);
        return dao.read(identity);
    }

    @Override
    public User findByLoginAndPassword(String login, String password) throws PersistentException {
        UserDao dao = transaction.createDao(UserDao.class);
        return dao.read(login, md5(password));
    }

    @Override
    public void save(User user) throws PersistentException {
        UserDao dao = transaction.createDao(UserDao.class);
        if(user.getIdentity() != null) {
            if(user.getPassword() != null) {
                user.setPassword(md5(user.getPassword()));
            } else {
                User oldUser = dao.read(user.getIdentity());
                user.setPassword(oldUser.getPassword());
            }
            dao.update(user);
        } else {
            user.setPassword(md5(new String()));
            user.setIdentity(dao.create(user));
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        UserDao dao = transaction.createDao(UserDao.class);
        dao.delete(identity);
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
