package by.popovich.last.service;

import by.popovich.last.entity.Coach;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface CoachService extends Service {
    Coach readById(Integer identity) throws PersistentException;

    List<Coach> readBySalary(double salary) throws PersistentException;

    List<Coach> readAll() throws PersistentException;

    void updateCurrentVisitors(final int numberOfCurrent,
                               final int coachId) throws PersistentException;

    void add(Coach coach) throws PersistentException;

    void update(Coach coach) throws PersistentException;

    void delete(Integer identity) throws PersistentException;
}
