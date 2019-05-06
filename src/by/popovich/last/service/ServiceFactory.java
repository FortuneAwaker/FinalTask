package by.popovich.last.service;

import by.popovich.last.exception.PersistentException;

public interface ServiceFactory {
    <Type extends Service> Type getService(Class<Type> key)
            throws PersistentException;

    void close();
}
