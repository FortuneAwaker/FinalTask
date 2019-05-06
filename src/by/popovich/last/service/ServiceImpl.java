package by.popovich.last.service;

import by.popovich.last.dao.Transaction;

public class ServiceImpl implements Service {
    protected Transaction transaction = null;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
