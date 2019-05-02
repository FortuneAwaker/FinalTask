package by.popovich.last.dao;

import by.popovich.last.entity.Coach;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface CoachDao extends DAO<Coach> {
    Coach readById(Integer id) throws PersistentException;

    List<Coach> read() throws PersistentException;

    List<Coach> readBySalary(int salary) throws PersistentException;

    List<Coach> readByMaxClients(int maxClients) throws PersistentException;
}
