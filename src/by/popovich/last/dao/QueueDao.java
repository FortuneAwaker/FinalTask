package by.popovich.last.dao;

import by.popovich.last.entity.Queue;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface QueueDao extends DAO<Queue> {
    List<Queue> read() throws PersistentException;

    List<Queue> readByWantedType(Integer wantedType) throws PersistentException;
}
