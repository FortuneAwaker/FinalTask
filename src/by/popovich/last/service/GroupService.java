package by.popovich.last.service;

import by.popovich.last.entity.Exercise;
import by.popovich.last.entity.Group;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface GroupService extends Service {
    Group readById(Integer identity) throws PersistentException;

    List<Group> readAll() throws PersistentException;

    List<Group> readGroupsByCoach(Integer coachId) throws PersistentException;

    void save(Group group) throws PersistentException;

    void delete(Integer identity) throws PersistentException;
}
