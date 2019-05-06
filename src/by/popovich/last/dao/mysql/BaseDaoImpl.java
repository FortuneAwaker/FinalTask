package by.popovich.last.dao.mysql;

import java.sql.Connection;

public class BaseDaoImpl {
    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
