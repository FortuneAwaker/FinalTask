package by.popovich.last.service;

import by.popovich.last.dao.PriceDao;
import by.popovich.last.entity.Price;
import by.popovich.last.exception.PersistentException;

import java.util.List;

public class PriceServiceImpl extends ServiceImpl implements PriceService {
    @Override
    public Price readById(Integer identity) throws PersistentException {
        PriceDao dao = transaction.createDao(PriceDao.class);
        return dao.read(identity);
    }

    @Override
    public List<Price> readAll() throws PersistentException {
        PriceDao dao = transaction.createDao(PriceDao.class);
        return dao.read();
    }

    @Override
    public List<Price> readByExerciseType(Integer type)
            throws PersistentException {
        PriceDao dao = transaction.createDao(PriceDao.class);
        return dao.readByExerciseType(type);
    }

    @Override
    public void save(Price price) throws PersistentException {
        PriceDao dao = transaction.createDao(PriceDao.class);
        if(price.getIdentity() != null) {
            dao.update(price);
        } else {
            price.setIdentity(dao.create(price));
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        PriceDao dao = transaction.createDao(PriceDao.class);
        dao.delete(identity);
    }
}
