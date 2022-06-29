package rs.ac.bg.fon.nprog.server.system_operation.service;


import rs.ac.bg.fon.nprog.commonlibrary.domain.Service;
import rs.ac.bg.fon.nprog.server.system_operation.AbstractSO;

/**
 * @author Dragon
 */
public class GetAllServicesSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        result = repository.findRecords(new Service(), null);
    }

    public Object getResult() {
        return result;
    }
}
