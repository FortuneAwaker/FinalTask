package by.popovich.last.service;

import by.popovich.last.entity.Queue;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public interface QueueService extends Service {
    Queue readById(Integer identity) throws PersistentException;

    List<Queue> readAll() throws PersistentException;

    List<Queue> readByWantedType(Integer wantedType) throws PersistentException;

    void save(Queue queue) throws PersistentException;

    void delete(Integer identity) throws PersistentException;
}
