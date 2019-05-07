package by.popovich.last.service;

import by.popovich.last.dao.GroupDao;
import by.popovich.last.entity.Group;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public class GroupServiceImpl extends ServiceImpl implements GroupService {
    @Override
    public Group readById(Integer identity) throws PersistentException {
        GroupDao dao = transaction.createDao(GroupDao.class);
        return dao.read(identity);
    }

    @Override
    public List<Group> readAll() throws PersistentException {
        GroupDao dao = transaction.createDao(GroupDao.class);
        return dao.read();
    }

    @Override
    public List<Group> readGroupsByCoach(Integer coachId)
            throws PersistentException {
        GroupDao dao = transaction.createDao(GroupDao.class);
        return dao.readByCoachId(coachId);
    }

    @Override
    public void save(Group group) throws PersistentException {
        GroupDao dao = transaction.createDao(GroupDao.class);
        if(group.getIdentity() != null) {
            dao.update(group);
        } else {
            group.setIdentity(dao.create(group));
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        GroupDao dao = transaction.createDao(GroupDao.class);
        dao.delete(identity);
    }
}
