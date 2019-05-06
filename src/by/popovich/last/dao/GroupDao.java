package by.popovich.last.dao;

import by.popovich.last.entity.Group;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface GroupDao extends DAO<Group> {
    Group readByCoachId(Integer id) throws PersistentException;

    List<Group> read() throws PersistentException;
}
