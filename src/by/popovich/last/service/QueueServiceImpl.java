package by.popovich.last.service;

import by.popovich.last.dao.QueueDao;
import by.popovich.last.entity.Queue;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public class QueueServiceImpl extends ServiceImpl implements QueueService {
    @Override
    public Queue readById(Integer identity) throws PersistentException {
        QueueDao dao = transaction.createDao(QueueDao.class);
        return dao.read(identity);
    }

    @Override
    public List<Queue> readAll() throws PersistentException {
        QueueDao dao = transaction.createDao(QueueDao.class);
        return dao.read();
    }

    @Override
    public List<Queue> readByWantedType(Integer wantedType) throws PersistentException {
        QueueDao dao = transaction.createDao(QueueDao.class);
        return dao.readByWantedType(wantedType);
    }

    @Override
    public void save(Queue queue) throws PersistentException {
        QueueDao dao = transaction.createDao(QueueDao.class);
        if(queue.getIdentity() != null) {
            dao.update(queue);
        } else {
            dao.create(queue);
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        QueueDao dao = transaction.createDao(QueueDao.class);
        dao.delete(identity);
    }
}
