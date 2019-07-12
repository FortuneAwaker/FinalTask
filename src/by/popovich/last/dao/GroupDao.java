package by.popovich.last.dao;

import by.popovich.last.entity.Group;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface GroupDao extends DAO<Group> {
    void updateCurrent(int numberOfCurrent,
                       int groupId) throws PersistentException;

    void addVisitor(final int groupId) throws PersistentException;

    void removeVisitor(final int groupId) throws PersistentException;

    List<Group> readByCoachId(Integer id) throws PersistentException;

    List<Group> readGroupsByType(final int exercisesTypeId)
            throws PersistentException;

    List<Group> read() throws PersistentException;
}
