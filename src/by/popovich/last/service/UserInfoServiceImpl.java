package by.popovich.last.service;

import by.popovich.last.dao.UserInfoDao;
import by.popovich.last.entity.Person;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public class UserInfoServiceImpl extends ServiceImpl
        implements UserInfoService {
    @Override
    public Person readById(Integer identity) throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        return dao.read(identity);
    }

    @Override
    public List<Person> readAll() throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        return dao.read();
    }

    @Override
    public void addInfo(Person person) throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        dao.create(person);
    }

    @Override
    public void updateInfo(Person person) throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        dao.update(person);
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        dao.delete(identity);
    }
}
