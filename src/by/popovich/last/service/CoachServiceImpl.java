package by.popovich.last.service;

import by.popovich.last.dao.CoachDao;
import by.popovich.last.entity.Coach;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public class CoachServiceImpl extends ServiceImpl implements CoachService {
    @Override
    public Coach readById(Integer identity) throws PersistentException {
        CoachDao dao = transaction.createDao(CoachDao.class);
        return dao.read(identity);
    }

    @Override
    public List<Coach> readBySalary(double salary) throws PersistentException {
        CoachDao dao = transaction.createDao(CoachDao.class);
        return dao.readBySalary(salary);
    }

    @Override
    public List<Coach> readAll() throws PersistentException {
        CoachDao dao = transaction.createDao(CoachDao.class);
        return dao.read();
    }

    @Override
    public void updateCurrentVisitors(int numberOfCurrent, int coachId)
            throws PersistentException {
        CoachDao dao = transaction.createDao(CoachDao.class);
        dao.updateCurrent(numberOfCurrent, coachId);
    }

    @Override
    public void add(Coach coach) throws PersistentException {
        CoachDao dao = transaction.createDao(CoachDao.class);
        dao.create(coach);
    }

    @Override
    public void update(Coach coach) throws PersistentException {
        CoachDao dao = transaction.createDao(CoachDao.class);
        dao.update(coach);
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        CoachDao dao = transaction.createDao(CoachDao.class);
        dao.delete(identity);
    }
}
