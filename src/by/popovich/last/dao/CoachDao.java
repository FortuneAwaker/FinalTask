package by.popovich.last.dao;

import by.popovich.last.entity.Coach;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface CoachDao extends DAO<Coach> {
    void updateCurrent(int numberOfCurrent,
                       int coachId) throws PersistentException;

    List<Coach> read() throws PersistentException;

    List<Coach> readBySalary(double salary) throws PersistentException;
}
