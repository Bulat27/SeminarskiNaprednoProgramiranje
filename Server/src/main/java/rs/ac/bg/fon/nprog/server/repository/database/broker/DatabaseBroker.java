
package rs.ac.bg.fon.nprog.server.repository.database.broker;

import rs.ac.bg.fon.nprog.server.repository.Repository;

/**
 *
 * @author Dragon
 * @param <T>
 */
public abstract class DatabaseBroker<T> implements Repository<T> {
    
    public abstract void connect() throws Exception;
    public abstract void closeConnection() throws Exception;
    public abstract void commitTransaction() throws Exception;
    public abstract void rollbackTransaction() throws Exception;
}
