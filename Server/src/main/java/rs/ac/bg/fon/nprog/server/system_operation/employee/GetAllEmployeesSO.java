package rs.ac.bg.fon.nprog.server.system_operation.employee;

import rs.ac.bg.fon.nprog.commonlibrary.domain.Employee;
import rs.ac.bg.fon.nprog.server.system_operation.AbstractSO;

/**
 *
 * @author Dragon
 */
public class GetAllEmployeesSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        result = repository.findRecords(new Employee(), null);
    }

    public Object getResult() {
        return result;
    }
}
