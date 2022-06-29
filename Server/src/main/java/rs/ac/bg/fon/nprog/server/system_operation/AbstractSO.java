package rs.ac.bg.fon.nprog.server.system_operation;


import rs.ac.bg.fon.nprog.server.repository.Repository;
import rs.ac.bg.fon.nprog.server.repository.database.broker.DatabaseBroker;
import rs.ac.bg.fon.nprog.server.repository.database.broker.impl.DatabaseBrokerImpl;

/**
 * @author Dragon
 */
public abstract class AbstractSO {

    protected final Repository repository;
    protected Object result;

    public AbstractSO() {
        repository = new DatabaseBrokerImpl();
    }

    protected AbstractSO(Repository repository) {
        this.repository = repository;
    }

    public void execute(Object param) throws Exception {
        try {
            precondition(param);
            startTransaction();
            executeOperation(param);
            commitTransaction();
            System.out.println("Successful operation!");
        } catch (Exception ex) {
            System.out.println("Unsuccessful operation!");
            rollbackTransaction();
            throw ex;
        } finally {
            closeConnection();
        }
    }

    protected abstract void precondition(Object param) throws Exception;

    protected abstract void executeOperation(Object param) throws Exception;

    private void startTransaction() throws Exception {
        ((DatabaseBroker) repository).connect();
    }

    private void commitTransaction() throws Exception {
        ((DatabaseBroker) repository).commitTransaction();
    }

    private void rollbackTransaction() throws Exception {
        ((DatabaseBroker) repository).rollbackTransaction();
    }

    private void closeConnection() throws Exception {
        ((DatabaseBroker) repository).closeConnection();
    }
}
