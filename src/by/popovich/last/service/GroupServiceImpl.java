package by.popovich.last.service;

import by.popovich.last.dao.GroupDao;
import by.popovich.last.dao.mysql.ExerciseDaoImpl;
import by.popovich.last.dao.mysql.GroupDaoImpl;
import by.popovich.last.dao.pool.ConnectionPool;
import by.popovich.last.entity.Exercise;
import by.popovich.last.entity.Group;
import by.popovich.last.exception.PersistentException;

import java.sql.Connection;
import java.util.List;

public class GroupServiceImpl extends ServiceImpl implements GroupService {
    @Override
    public Group readById(Integer identity) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        GroupDaoImpl dao = new GroupDaoImpl();
        dao.setConnection(connection);
        return dao.read(identity);
    }

    @Override
    public List<Group> readAll() throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        GroupDaoImpl dao = new GroupDaoImpl();
        dao.setConnection(connection);
        return dao.read();
    }

    @Override
    public List<Group> readGroupsByCoach(final Integer coachId)
            throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        GroupDaoImpl dao = new GroupDaoImpl();
        dao.setConnection(connection);
        return dao.readByCoachId(coachId);
    }

    @Override
    public List<Group> readGroupsByTypeId(final int exercisesTypeId) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        GroupDaoImpl dao = new GroupDaoImpl();
        dao.setConnection(connection);
        return dao.readGroupsByType(exercisesTypeId);
    }

    @Override
    public List<Group> readGroupsByTypeName(String exercisesTypeName) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        GroupDaoImpl groupDao = new GroupDaoImpl();
        ExerciseDaoImpl exerciseDao = new ExerciseDaoImpl();
        exerciseDao.setConnection(connection);
        groupDao.setConnection(connection);
        Exercise exercise = exerciseDao.readIdByName(exercisesTypeName);
        return groupDao.readGroupsByType(exercise.getIdentity());
    }

    @Override
    public void save(Group group) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        GroupDaoImpl dao = new GroupDaoImpl();
        dao.setConnection(connection);
        if (group.getIdentity() != null) {
            dao.update(group);
        } else {
            group.setIdentity(dao.create(group));
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        GroupDaoImpl dao = new GroupDaoImpl();
        dao.setConnection(connection);
        dao.delete(identity);
    }

    @Override
    public void updateCurrentVisitors(int numberOfCurrent, int groupId)
            throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        GroupDaoImpl dao = new GroupDaoImpl();
        dao.setConnection(connection);
        dao.updateCurrent(numberOfCurrent, groupId);
    }

    @Override
    public void addVisitor(int groupId) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        GroupDaoImpl dao = new GroupDaoImpl();
        dao.setConnection(connection);
        dao.addVisitor(groupId);
    }

    @Override
    public void removeVisitor(int groupId) throws PersistentException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        GroupDaoImpl dao = new GroupDaoImpl();
        dao.setConnection(connection);
        dao.removeVisitor(groupId);
    }


}
