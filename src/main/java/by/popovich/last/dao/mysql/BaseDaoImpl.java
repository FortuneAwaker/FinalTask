package by.popovich.last.dao.mysql;

import java.sql.Connection;

/**
 * Base DAO implementation.
 */
public class BaseDaoImpl {
    /**
     * Connection.
     */
    protected Connection connection;

    /**
     * Setter.
     * @param con connection.
     */
    public void setConnection(final Connection con) {
        this.connection = con;
    }
}
